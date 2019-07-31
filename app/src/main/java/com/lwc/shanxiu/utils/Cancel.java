package com.lwc.shanxiu.utils;

import com.lwc.shanxiu.R;
import com.lwc.shanxiu.bean.ResponseBean;
import com.lwc.shanxiu.configs.TApplication;

/**
 * 描述：用于取消用户操作的工具类
 * 
 * @author kpxingxing
 * @date 2014-3-17
 * 
 */
public class Cancel {
	/** 请求去取消状态码 */
	public static final String RESPONSE_STATUS_CANCEL = "-10000";

	/**
	 * 
	 * 描述：检测是否被操作取消
	 * 
	 * createTime 2014-3-17 上午11:24:40 createAuthor kpxingxing
	 * 
	 * updateTime 2014-3-17 上午11:24:40 updateAuthor kpxingxing updateInfo
	 * 
	 * @param object
	 * @return
	 */
	public static boolean checkCancel(Object object) {
		if (object instanceof ResponseBean) {
			ResponseBean bean = (ResponseBean) object;
			if (RESPONSE_STATUS_CANCEL.equals(bean.getStatus())) {
				// Log.w(ProgressDialogUtil.TAG, bean.getInfo());
				return true;
			}
		}
		try {
			checkInterrupted();
		} catch (CancelException e) {
			// Log.w(ProgressDialogUtil.TAG, e.toString());
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：检测是否被操作取消
	 *
	 * createTime 2014-3-17 下午3:24:03 createAuthor kpxingxing
	 *
	 * updateTime 2014-3-17 下午3:24:03 updateAuthor kpxingxing updateInfo
	 * 
	 * @return
	 */
	public static boolean checkCancel() {
		try {
			checkInterrupted();
		} catch (CancelException e) {
			// Log.w(ProgressDialogUtil.TAG, e.toString());
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 描述：检测当前线程是否被取消
	 * 
	 * createTime 2014-3-17 上午11:33:05 createAuthor kpxingxing
	 * 
	 * updateTime 2014-3-17 上午11:33:05 updateAuthor kpxingxing updateInfo
	 * 
	 * @throws CancelException
	 *         抛出取消的异常
	 */
	public static void checkInterrupted() throws CancelException {
		checkInterrupted(Thread.currentThread());
	}

	/**
	 * 
	 * 描述：检测当前线程是否被取消
	 * 
	 * createTime 2014-3-17 上午11:33:31 createAuthor kpxingxing
	 * 
	 * updateTime 2014-3-17 上午11:33:31 updateAuthor kpxingxing updateInfo
	 * 
	 * @param currentThread
	 *        当前线程
	 * @throws CancelException
	 *         抛出取消的异常
	 */
	public static void checkInterrupted(Thread currentThread) throws CancelException {
		if (currentThread != null && currentThread.isInterrupted()) {
			String cancelString = TApplication.context.getString(R.string.exception_cancel);
			CancelException exception = new CancelException(RESPONSE_STATUS_CANCEL, cancelString);
			throw exception;
		}
	}

	/**
	 * 描述：用户取消操作之后，抛出的异常
	 * 
	 * @author kpxingxing
	 * @date 2014-3-17
	 * 
	 */
	public static class CancelException extends Exception {
		private static final long serialVersionUID = 1L;

		/** 状态码 */
		public String mStatus;
		/** 取消的信息 */
		public String mInfo;

		public CancelException(String status, String info) {
			mStatus = status;
			mInfo = info;
		}
	}

	/**
	 * 描述：用于DataBaseOperQueue.addTask的RunnableTask。
	 * 
	 * @author kpxingxing
	 * @date 2014-3-17
	 * 
	 */
	public static abstract class RunnableTask implements Runnable {

		/** 能否被打断 */
		private boolean mCanInterrupt = true;
		/** 是否被打断 */
		private boolean mIsInterrupted = false;
		/** 执行的当前线程 */
		private Thread mCurrentThread;

		public RunnableTask() {
			mCanInterrupt = true;
		}

		public RunnableTask(boolean canInterrupt) {
			mCanInterrupt = canInterrupt;
		}

		/**
		 * 必须重载并且在子类的run方法中调用
		 */
		@Override
		public void run() {
			if (mCanInterrupt) {
				mCurrentThread = Thread.currentThread();
				if (mIsInterrupted) {
					mCurrentThread.interrupt();
				}
			}
		}

		/**
		 * 
		 * 描述：打断线程的执行
		 * 
		 * createTime 2014-3-17 上午10:58:24 createAuthor kpxingxing
		 * 
		 * updateTime 2014-3-17 上午10:58:24 updateAuthor kpxingxing updateInfo
		 * 
		 */
		public void interrupt() {
			if (mCanInterrupt) {
				mIsInterrupted = true;
				if (mCurrentThread != null) {
					mCurrentThread.interrupt();
				}
			}
		}

		/**
		 * 
		 * 描述：判断线程是否被打断
		 * 
		 * createTime 2014-3-17 上午10:59:00 createAuthor kpxingxing
		 * 
		 * updateTime 2014-3-17 上午10:59:00 updateAuthor kpxingxing updateInfo
		 * 
		 * @return
		 */
		public boolean isInterrupted() {
			return mIsInterrupted;
		}
	}

}
