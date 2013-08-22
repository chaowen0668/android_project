package com.corner23.android.beautyclocklivewallpaper.asynctasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import com.corner23.android.beautyclocklivewallpaper.Settings;
import com.corner23.android.beautyclocklivewallpaper.services.UpdateService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class FetchBeautyPictureTask extends AsyncTask<Integer, Void, Integer> {

	private static final int IO_BUFFER_SIZE = 4096;
		
	private static final int BCLW_FETCH_STATE_OTHER_FAILED = -1;
	private static final int BCLW_FETCH_STATE_SUCCESS = 0;
	private static final int BCLW_FETCH_STATE_FILE_NOT_FOUND = 1;
	private static final int BCLW_FETCH_STATE_TIMEOUT = 2;
	private static final int BCLW_FETCH_STATE_IO_ERROR = 3;

	private static final String TAG = "FetchBeautyPictureTask";

	private static final String ARTHUR_PICTURE_URL = "http://www.arthur.com.tw/photo/images/400/%02d%02d.JPG";
	private static final String CLOCKM_PICTURE_URL = "http://www.clockm.com/tw/img/clk/hour/%02d%02d.jpg";
	private static final String BIJIN_PICTURE_URL_L = "http://www.bijint.com/assets/pict/bijin/590x450/%02d%02d.jpg";
	private static final String BIJIN_PICTURE_URL = "http://www.bijint.com/assets/pict/bijin/240x320/%02d%02d.jpg";
	private static final String BIJIN_KOREA_PICTURE_URL_L = "http://www.bijint.com/assets/pict/kr/590x450/%02d%02d.jpg";
	private static final String BIJIN_KOREA_PICTURE_URL = "http://www.bijint.com/assets/pict/kr/240x320/%02d%02d.jpg";
	private static final String BIJIN_GAL_PICTURE_URL_L = "http://gal.bijint.com/assets/pict/gal/590x450/%02d%02d.jpg";
	private static final String BIJIN_GAL_PICTURE_URL = "http://gal.bijint.com/assets/pict/gal/240x320/%02d%02d.jpg";
	private static final String BIJIN_CC_PICTURE_URL = "http://www.bijint.com/assets/pict/cc/590x450/%02d%02d.jpg";
	private static final String BINAN_PICTURE_URL_L = "http://www.bijint.com/assets/pict/binan/590x450/%02d%02d.jpg";
	private static final String BINAN_PICTURE_URL = "http://www.bijint.com/assets/pict/binan/240x320/%02d%02d.jpg";
	private static final String BIJIN_HK_PICTURE_URL_L = "http://www.bijint.com/assets/pict/hk/590x450/%02d%02d.jpg";
	private static final String BIJIN_HK_PICTURE_URL = "http://www.bijint.com/assets/pict/hk/240x320/%02d%02d.jpg";
	
	private static final String BIJIN_NIIGATA_PICTURE_URL = "http://www.bijint.com/niigata/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_KAGOSHIMA_PICTURE_URL = "http://www.bijint.com/kagoshima/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_NAGOYA_PICTURE_URL = "http://www.bijint.com/nagoya/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_KAGAWA_PICTURE_URL = "http://www.bijint.com/kagawa/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_OKAYMA_PICTURE_URL = "http://www.bijint.com/okayama/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_FUKUOKA_PICTURE_URL = "http://www.bijint.com/fukuoka/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_KANAZAWA_PICTURE_URL = "http://www.bijint.com/kanazawa/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_KYOTO_PICTURE_URL = "http://www.bijint.com/kyoto/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_SENDAI_PICTURE_URL = "http://www.bijint.com/sendai/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_HOKKAIDO_PICTURE_URL = "http://www.bijint.com/hokkaido/tokei_images/%02d%02d.jpg";
	private static final String BIJIN_WASEDASTYLE_PICTURE_URL = "http://www.bijint.com/wasedastyle/tokei_images/%02d%02d.jpg";

	private static final String LOVELY_TIME_PICTURE_URL = "http://gameflier.lovelytime.com.tw/photo/%02d%02d.JPG";
	private static final String WRETCH_PICTURE_URL = "http://tw.yimg.com/i/tw/wretch/beautyclockweb/%02d%02d.jpg";
	private static final String WRETCH_PICTURE_URL_L = "http://l.yimg.com/f/i/tw/wretch/beautyclock/%02d%02d.jpg";
	private static final String AVTOKEI_PICTURE_URL = "http://www.avtokei.jp/images/clocks/%02d/%02d%02d.jpg";

	private static final String SDCARD_BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/BeautyClock/pic/%s/%02d%02d.jpg";
	private static final String ARTHUR_PICTURE = "arthur";
	private static final String CLOCKM_PICTURE = "clockm";
	private static final String BIJIN_PICTURE = "bijin";
	private static final String BIJIN_PICTURE_L = "bijin_l";
	private static final String BIJIN_KOREA_PICTURE = "bijin-kr";
	private static final String BIJIN_KOREA_PICTURE_L = "bijin-kr_l";
	private static final String BIJIN_GAL_PICTURE = "bijin-gal";
	private static final String BIJIN_GAL_PICTURE_L = "bijin-gal_l";
	private static final String BIJIN_CC_PICTURE = "bijin-cc";
	private static final String BINAN_PICTURE = "binan";
	private static final String BINAN_PICTURE_L = "binan_l";
	private static final String BIJIN_HK_PICTURE = "bijin-hk";
	private static final String BIJIN_HK_PICTURE_L = "bijin-hk_l";
	
	private static final String BIJIN_NIIGATA_PICTURE = "niigata";
	private static final String BIJIN_KAGOSHIMA_PICTURE = "kagoshima";
	private static final String BIJIN_NAGOYA_PICTURE = "nagoya";
	private static final String BIJIN_KAGAWA_PICTURE = "kagawa";
	private static final String BIJIN_OKAYMA_PICTURE = "okayama";
	private static final String BIJIN_FUKUOKA_PICTURE = "fukuoka";
	private static final String BIJIN_KANAZAWA_PICTURE = "kanazawa";
	private static final String BIJIN_KYOTO_PICTURE = "kyoto";
	private static final String BIJIN_SENDAI_PICTURE = "sendai";
	private static final String BIJIN_HOKKAIDO_PICTURE = "hokkaido";
	private static final String BIJIN_WASEDASTYLE_PICTURE = "wasedastyle";
	
	private static final String LOVELY_TIME_PICTURE = "lovely";
	private static final String WRETCH_PICTURE = "wretch";
	private static final String WRETCH_PICTURE_L = "wretch_l";
	private static final String AVTOKEI_PICTURE = "av";
	private static final String CUSTOM_PICTURE = "custom";
	
	private static final String CACHE_FILE_PATH = "%02d%02d.jpg";
	
	private int mHour, mMinute;
	private int mPictureSource;
	private boolean mFetchLargerPicture;
	private boolean mSaveCopy;
	private Context mContext = null;
	private ConnectivityManager cm = null;
	private boolean bCancel = false;

	public void saveSourcePath() {
		String path = (new File(getPATH())).getParent();
		
		Editor editor = mContext.getSharedPreferences(Settings.SHARED_PREFS_NAME, 0).edit();
		editor.putString(Settings.PREF_INTERNAL_PICTURE_PATH, path);
		editor.commit();
		
		Log.d(TAG, "saving path .." + path);
	}
	
	public FetchBeautyPictureTask(Context context, ConnectivityManager connmgr, int source, boolean getlarge, boolean savecopy) {
		mContext = context;		
		cm = connmgr;
		mPictureSource = source;
		mFetchLargerPicture = getlarge;
		mSaveCopy = savecopy;
	}
	
	private String getPATH() {
		String URLstr = null;
		switch (mPictureSource) {
		default:
		case 0:	URLstr = String.format(SDCARD_BASE_PATH, ARTHUR_PICTURE, mHour, mMinute); break;
		case 1:	URLstr = String.format(SDCARD_BASE_PATH, CLOCKM_PICTURE, mHour, mMinute); break;
		case 2: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? BIJIN_PICTURE_L : BIJIN_PICTURE, mHour, mMinute); break;
		case 3: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? BIJIN_KOREA_PICTURE_L : BIJIN_KOREA_PICTURE, mHour, mMinute); break;
		case 4: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? BIJIN_HK_PICTURE_L : BIJIN_HK_PICTURE, mHour, mMinute); break;
		case 5:	URLstr = String.format(SDCARD_BASE_PATH, BIJIN_NIIGATA_PICTURE, mHour, mMinute); break;
		case 6:	URLstr = String.format(SDCARD_BASE_PATH, BIJIN_KAGOSHIMA_PICTURE, mHour, mMinute); break;
		case 7: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_NAGOYA_PICTURE, mHour, mMinute); break;
		case 8: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_KAGAWA_PICTURE, mHour, mMinute); break;
		case 9: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_OKAYMA_PICTURE, mHour, mMinute); break;
		case 10: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_FUKUOKA_PICTURE, mHour, mMinute); break;
		case 11: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_KANAZAWA_PICTURE, mHour, mMinute); break;
		case 12: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_KYOTO_PICTURE, mHour, mMinute); break;
		case 13: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_SENDAI_PICTURE, mHour, mMinute); break;
		case 14: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_HOKKAIDO_PICTURE, mHour, mMinute); break;
		case 15: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_WASEDASTYLE_PICTURE, mHour, mMinute); break;
		case 16: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? BIJIN_GAL_PICTURE_L : BIJIN_GAL_PICTURE, mHour, mMinute); break;
		case 17: URLstr = String.format(SDCARD_BASE_PATH, BIJIN_CC_PICTURE, mHour, mMinute); break;
		case 18: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? BINAN_PICTURE_L : BINAN_PICTURE, mHour, mMinute); break;
		case 19: URLstr = String.format(SDCARD_BASE_PATH, LOVELY_TIME_PICTURE, mHour, mMinute); break;
		case 20: URLstr = String.format(SDCARD_BASE_PATH, mFetchLargerPicture ? WRETCH_PICTURE_L : WRETCH_PICTURE, mHour, mMinute); break;
		case 98: URLstr = String.format(SDCARD_BASE_PATH, CUSTOM_PICTURE, mHour, mMinute); break;
		case 99: URLstr = String.format(SDCARD_BASE_PATH, AVTOKEI_PICTURE, mHour, mMinute); break;
		}
		
		return URLstr;
	}

	private String getURL() {
		String URLstr = null;
		switch (mPictureSource) {
		default:
		case 0:	URLstr = String.format(ARTHUR_PICTURE_URL, mHour, mMinute); break;
		case 1:	URLstr = String.format(CLOCKM_PICTURE_URL, mHour, mMinute); break;
		case 2: URLstr = String.format(mFetchLargerPicture ? BIJIN_PICTURE_URL_L : BIJIN_PICTURE_URL, mHour, mMinute); break;
		case 3: URLstr = String.format(mFetchLargerPicture ? BIJIN_KOREA_PICTURE_URL_L : BIJIN_KOREA_PICTURE_URL, mHour, mMinute); break;
		case 4: URLstr = String.format(mFetchLargerPicture ? BIJIN_HK_PICTURE_URL_L : BIJIN_HK_PICTURE_URL, mHour, mMinute); break;
		case 5:	URLstr = String.format(BIJIN_NIIGATA_PICTURE_URL, mHour, mMinute); break;
		case 6:	URLstr = String.format(BIJIN_KAGOSHIMA_PICTURE_URL, mHour, mMinute); break;
		case 7: URLstr = String.format(BIJIN_NAGOYA_PICTURE_URL, mHour, mMinute); break;
		case 8: URLstr = String.format(BIJIN_KAGAWA_PICTURE_URL, mHour, mMinute); break;
		case 9: URLstr = String.format(BIJIN_OKAYMA_PICTURE_URL, mHour, mMinute); break;
		case 10: URLstr = String.format(BIJIN_FUKUOKA_PICTURE_URL, mHour, mMinute); break;
		case 11: URLstr = String.format(BIJIN_KANAZAWA_PICTURE_URL, mHour, mMinute); break;
		case 12: URLstr = String.format(BIJIN_KYOTO_PICTURE_URL, mHour, mMinute); break;
		case 13: URLstr = String.format(BIJIN_SENDAI_PICTURE_URL, mHour, mMinute); break;
		case 14: URLstr = String.format(BIJIN_HOKKAIDO_PICTURE_URL, mHour, mMinute); break;
		case 15: URLstr = String.format(BIJIN_WASEDASTYLE_PICTURE_URL, mHour, mMinute); break;
		case 16: URLstr = String.format(mFetchLargerPicture ? BIJIN_GAL_PICTURE_URL_L : BIJIN_GAL_PICTURE_URL, mHour, mMinute); break;
		case 17: URLstr = String.format(BIJIN_CC_PICTURE_URL, mHour, mMinute); break;
		case 18: URLstr = String.format(mFetchLargerPicture ? BINAN_PICTURE_URL_L : BINAN_PICTURE_URL, mHour, mMinute); break;
		case 19: URLstr = String.format(LOVELY_TIME_PICTURE_URL, mHour, mMinute); break;
		case 20: URLstr = String.format(mFetchLargerPicture ? WRETCH_PICTURE_URL_L : WRETCH_PICTURE_URL, mHour, mMinute); break;
		case 99: URLstr = String.format(AVTOKEI_PICTURE_URL, mHour, mHour, mMinute); break;
		}
		
		return URLstr;
	}
	
	private String getReferer() {
		String referer = null;
		switch (mPictureSource) {
		case 2: referer = "http://www.bijint.com/jp/"; break;
		case 3: referer = "http://www.bijint.com/kr/"; break;
		case 4: referer = "http://www.bijint.com/hk/"; break;
		case 5: referer = "http://www.bijint.com/niigata/"; break;
		case 6: referer = "http://www.bijint.com/kagoshima/"; break;
		case 7: referer = "http://www.bijint.com/nagoya/"; break;
		case 8: referer = "http://www.bijint.com/kagawa/"; break;
		case 9: referer = "http://www.bijint.com/okayama/"; break;
		case 10: referer = "http://www.bijint.com/fukuoka/"; break;
		case 11: referer = "http://www.bijint.com/kanazawa/"; break;
		case 12: referer = "http://www.bijint.com/kyoto/"; break;
		case 13: referer = "http://www.bijint.com/sendai/"; break;
		case 14: referer = "http://www.bijint.com/hokkaido/"; break;
		case 15: referer = "http://www.bijint.com/wasedastyle/"; break;
		case 16: referer = "http://gal.bijint.com/"; break;
		case 17: referer = "http://www.bijint.com/cc/"; break;
		case 18: referer = "http://www.bijint.com/binan/"; break;
		case 99: referer = "http://www.avtokei.jp/index.html"; break;
		}
		
		return referer;
	}
	
	private boolean saveBitmapToFile(byte[] bitmap, File new_file) {
		if (bitmap == null || new_file == null) {
			Log.e(TAG, "Null parameters in saveBitmapToFile");
			return false;
		}
		
		try {
			if (!new_file.getParentFile().exists()) {
				new_file.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(new_file);
			out.write(bitmap, 0, bitmap.length);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.e(TAG, "Error saving bitmap to file: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	private byte[] fetchBeautyPictureBitmapFromURL(URL url, String referer) throws IOException {
		byte[] bitmap = null;
		InputStream in = null;
		OutputStream out = null;
		URLConnection urlc = null;
		Proxy httpProxy = Proxy.NO_PROXY;

		try {
			if (cm != null) {
				// network is turned off by user
				if (!cm.getBackgroundDataSetting()) {
					Log.i(TAG, "background transfer disabled..");
					return null;
				}
				
				// network is not connected
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni != null && ni.getState() != NetworkInfo.State.CONNECTED) {
					Log.i(TAG, "network down");
					return null;
				}
				
				// if we're using WIFI, then there's no proxy from APN
				if (ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI) {
					httpProxy = Proxy.NO_PROXY;
				} else {
					String httpProxyHost = android.net.Proxy.getDefaultHost();
					Integer httpProxyPort = android.net.Proxy.getDefaultPort();
					if (httpProxyHost != null && httpProxyPort != null) {
						SocketAddress proxyAddress = new InetSocketAddress(httpProxyHost, httpProxyPort);
						httpProxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
					} else {
						httpProxy = Proxy.NO_PROXY;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "Error setting network env: " + e.getMessage());
		}
		
		try {
			urlc = url.openConnection(httpProxy);
			urlc.setRequestProperty("User-Agent", "Mozilla/5.0");
			if (referer != null) {
				urlc.setRequestProperty("Referer", referer);
			}
			urlc.setReadTimeout(10000);
			urlc.setConnectTimeout(10000);
			
			in = new BufferedInputStream(urlc.getInputStream(), IO_BUFFER_SIZE);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
			copy(in, out);
			out.flush();

			bitmap = dataStream.toByteArray();
			// bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		} finally {
			closeStream(in);
			closeStream(out);
		}

		return bitmap;
	}

	private int copy(InputStream in, OutputStream out) throws IOException, SocketTimeoutException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read, size = 0;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
			size += read;
		}
		return size;
	}

	private void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		Log.i(TAG, "doInBackground:FetchBeautyPictureTask");
		int ret = BCLW_FETCH_STATE_OTHER_FAILED;
		
		mHour = params[0];
		mMinute = params[1];

		try {
			// check file in sd card first
			String _path_sdcard = getPATH();
			File _file_sdcard = new File(_path_sdcard);
			if (_file_sdcard.exists()) {
				Log.d(TAG, "File in SD card:" + _path_sdcard);
				return BCLW_FETCH_STATE_SUCCESS;
			}

			// check application cache data
			String _path_cache = String.format(CACHE_FILE_PATH, mHour, mMinute);
			File _file_cache = new File(mContext.getCacheDir(), _path_cache);
			if (_file_cache.exists()) {
				Log.d(TAG, "File in cache");
				return BCLW_FETCH_STATE_SUCCESS;
			}

			URL mURL = new URL(getURL());
			byte[] bitmap = fetchBeautyPictureBitmapFromURL(mURL, getReferer());
			if (bitmap != null) {
				Log.d(TAG, "saving..");
				Log.d(TAG, mURL.toString());
				if (mSaveCopy) {
					Log.d(TAG, "saving.. to sd");
					saveBitmapToFile(bitmap, _file_sdcard);
				} else {
					saveBitmapToFile(bitmap, _file_cache);
					_file_cache.setReadable(true, false);
				}

				ret = BCLW_FETCH_STATE_SUCCESS;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return BCLW_FETCH_STATE_FILE_NOT_FOUND;
		} catch (IOException e) {
			e.printStackTrace();
			ret = BCLW_FETCH_STATE_IO_ERROR;
			if (cm != null) {
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni != null && ni.isConnected()) {
					ret = BCLW_FETCH_STATE_TIMEOUT;
				}
			}
		}
		return ret;
	}
	
	protected void onPostExecute(Integer ret) {
		Log.i(TAG, "onPostExecute:FetchBeautyPictureTask");
		if (bCancel) {
			Log.i(TAG, "Canceled.. ");
			return;
		}
		Intent intent = new Intent(mContext, UpdateService.class);
		intent.putExtra("fetch_pictures_ret", true);
		
		if (ret == BCLW_FETCH_STATE_TIMEOUT) {
			Log.i(TAG, "timeout, startToFetchBeautyPicture again");
			intent.putExtra("timeout", true);
		} else if (ret == BCLW_FETCH_STATE_SUCCESS) {
			intent.putExtra("fetch_success", true);
		} else {
			intent.putExtra("fetch_success", false);
		}
		
		mContext.startService(intent);		
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		bCancel = true;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if (mPictureSource == Settings.ID_CUSTOM_TOKEI) {
			this.cancel(true);
		}
	}
}
	