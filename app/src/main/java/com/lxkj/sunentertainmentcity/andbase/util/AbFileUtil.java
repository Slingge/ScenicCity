/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lxkj.sunentertainmentcity.andbase.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.lxkj.sunentertainmentcity.andbase.global.AbAppConfig;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbFileUtil.java 
 * 描述：文件操作类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-18 下午11:52:13
 */
public class AbFileUtil {
	
	/** 默认APP根目录. */
	private static String downloadRootDir = null;
	
    /** 默认下载图片文件目录. */
	private static String imageDownloadDir = null;
    
    /** 默认下载文件目录. */
	private static String fileDownloadDir = null;
	
	/** 默认缓存目录. */
	private static String cacheDownloadDir = null;
	
	/** 默认下载数据库文件的目录. */
	private static String dbDownloadDir = null;
	
	/** 剩余空间大于200M才使用SD缓存. */
	private static int freeSdSpaceNeededToCache = 200*1024*1024;

	/**
	 * 描述：SD卡是否能用.
	 *
	 * @return true 可用,false不可用
	 */
	public static boolean isCanUseSD() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Gets the file download dir.
	 *
	 * @param context the context
	 * @return the file download dir
	 */
	public static String getFileDownloadDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return fileDownloadDir;
	}


	/**
	 * 描述：获取src中的图片资源.
	 *
	 * @param src 图片的src路径，如（“image/arrow.png”）
	 * @return Bitmap 图片
	 */
	public static Bitmap getBitmapFromSrc(String src){
		Bitmap bit = null;
		try {
			bit = BitmapFactory.decodeStream(AbFileUtil.class.getResourceAsStream(src));
		} catch (Exception e) {
			AbLogUtil.d(AbFileUtil.class, "获取图片异常："+e.getMessage());
		}
		return bit;
	}


	/**
	 * 描述：初始化存储目录.
	 *
	 * @param context the context
	 */
	public static void initFileDir(Context context){

		PackageInfo info = AbAppUtil.getPackageInfo(context);

		//默认下载文件根目录.
		String downloadRootPath = File.separator + File.separator +"temp"+ File.separator;

		//默认下载图片文件目录.
		String imageDownloadPath = downloadRootPath + AbAppConfig.DOWNLOAD_IMAGE_DIR + File.separator;

		//默认下载文件目录.
		String fileDownloadPath = downloadRootPath + AbAppConfig.DOWNLOAD_FILE_DIR + File.separator;

		//默认缓存目录.
		String cacheDownloadPath = downloadRootPath + AbAppConfig.CACHE_DIR + File.separator;

		//默认DB目录.
		String dbDownloadPath = downloadRootPath + AbAppConfig.DB_DIR + File.separator;

		try {
			if(!isCanUseSD()){
				return;
			}else{

				File root = Environment.getExternalStorageDirectory();
				File downloadDir = new File(root.getAbsolutePath() + downloadRootPath);
				if(!downloadDir.exists()){
					downloadDir.mkdirs();
				}
				downloadRootDir = downloadDir.getPath();

				File cacheDownloadDirFile = new File(root.getAbsolutePath() + cacheDownloadPath);
				if(!cacheDownloadDirFile.exists()){
					cacheDownloadDirFile.mkdirs();
				}
				cacheDownloadDir = cacheDownloadDirFile.getPath();

				File imageDownloadDirFile = new File(root.getAbsolutePath() + imageDownloadPath);
				if(!imageDownloadDirFile.exists()){
					imageDownloadDirFile.mkdirs();
				}
				imageDownloadDir = imageDownloadDirFile.getPath();

				File fileDownloadDirFile = new File(root.getAbsolutePath() + fileDownloadPath);
				if(!fileDownloadDirFile.exists()){
					fileDownloadDirFile.mkdirs();
				}
				fileDownloadDir = fileDownloadDirFile.getPath();

				File dbDownloadDirFile = new File(root.getAbsolutePath() + dbDownloadPath);
				if(!dbDownloadDirFile.exists()){
					dbDownloadDirFile.mkdirs();
				}
				dbDownloadDir = dbDownloadDirFile.getPath();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the cache download dir.
	 *
	 * @param context the context
	 * @return the cache download dir
	 */
	public static String getCacheDownloadDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return cacheDownloadDir;
	}


	/**
	 * 获取文件名（.后缀），外链模式和通过网络获取.
	 *
	 * @param url 文件地址
	 * @param response the response
	 * @return 文件名
	 */
	public static String getCacheFileNameFromUrl(String url, HttpResponse response){
		if(AbStrUtil.isEmpty(url)){
			return null;
		}
		String name = null;
		try {
			//获取后缀
			String suffix = getMIMEFromUrl(url,response);
			if(AbStrUtil.isEmpty(suffix)){
				suffix = ".ab";
			}
			name = Md5Util.MD5(url)+suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}


	/**
	 * 获取文件后缀，本地和网络.
	 *
	 * @param url 文件地址
	 * @param response the response
	 * @return 文件后缀
	 */
	public static String getMIMEFromUrl(String url, HttpResponse response){

		if(AbStrUtil.isEmpty(url)){
			return null;
		}
		String mime = null;
		try {
			//获取后缀
			if(url.lastIndexOf(".")!=-1){
				mime = url.substring(url.lastIndexOf("."));
				if(mime.indexOf("/")!=-1 || mime.indexOf("?")!=-1 || mime.indexOf("&")!=-1){
					mime = null;
				}
			}
			if(AbStrUtil.isEmpty(mime)){
				//获取文件名  这个效率不高
				String fileName = getRealFileName(response);
				if(fileName!=null && fileName.lastIndexOf(".")!=-1){
					mime = fileName.substring(fileName.lastIndexOf("."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mime;
	}

	/**
	 * 获取真实文件名（xx.后缀），通过网络获取.
	 *
	 * @param response the response
	 * @return 文件名
	 */
	public static String getRealFileName(HttpResponse response){
		String name = null;
		try {
			if(response == null){
				return name;
			}
			//获取文件名
			Header[] headers = response.getHeaders("content-disposition");
			for(int i=0;i<headers.length;i++){
				Matcher m = Pattern.compile(".*filename=(.*)").matcher(headers[i].getValue());
				if (m.find()){
					name =  m.group(1).replace("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AbLogUtil.e(AbFileUtil.class, "网络上获取文件名失败");
		}
		return name;
	}



}
