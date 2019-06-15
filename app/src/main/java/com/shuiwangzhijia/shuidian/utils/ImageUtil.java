package com.shuiwangzhijia.shuidian.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 摘要: 该文件中的压缩方法会进行两次压缩<br>
 * <p>
 * 1.使用鲁班的压缩算法进行压缩并保存文件，目的：将大图上传到服务器<br>
 * 2.使用自定义算法压缩，目的：用来显示，给用户观看<br>
 * 鲁班项目地址：<a href = "https://github.com/Curzibn/Luban">点击查看</a><br>
 * <p>
 * Created by yangyang on 2018/5/25.
 */
public class ImageUtil {

    private static ImageUtil mImageUtil;

    private ImageUtil() {
    }

    public static ImageUtil getInstance() {
        if (null == mImageUtil) {
            synchronized (ImageUtil.class) {
                if (null == mImageUtil) {

                    mImageUtil = new ImageUtil();
                }
            }
        }
        return mImageUtil;
    }


    /**
     * get bitmap form uri.
     *
     * @param context
     * @param uri     image uri
     * @return
     * @throws IOException
     */
    public Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * 压缩图片，目标uri
     *
     * @param context
     * @param uri
     * @param requestWith   需要压缩到的宽度
     * @param requestHeight 需要压缩到的高度
     * @param filePath      bitmap保存到本地的文件路径
     * @return
     * @throws Exception
     */
    public Bitmap compressFromImageUri(Context context, Uri uri, int requestWith, int requestHeight,
                                       String filePath) throws Exception {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);


        /**** save big image for to server start *****/
        options.inSampleSize = computeSize(options);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        parcelFileDescriptor.close();
        Bitmap rotationBitmap = rotationBitmap(bitmap, getFilePath(context, uri));

        saveBitmapToLocal(60, filePath, rotationBitmap);
        rotationBitmap.recycle();
        /**** save big image for to server end *****/

        Bitmap smallBitmap = compress2SmallBitmap(requestWith, requestHeight, filePath, options);


        return smallBitmap;
    }


    /**
     * 压缩图片，目标文件
     *
     * @param originalFile  原文件
     * @param requestWith   需要压缩到的宽度
     * @param requestHeight 需要压缩到的高度
     * @param filePath      压缩后文件的存储路径
     * @return
     * @throws Exception
     */
    public Bitmap compressFromImageFile(File originalFile,
                                        int requestWith, int requestHeight, String filePath) throws Exception {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(originalFile.getAbsolutePath(), options);

        // 计算压缩比例
        options.inSampleSize = computeSize(options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), options);

        Bitmap rotationBitmap = rotationBitmap(bitmap, originalFile.getAbsolutePath());
        saveBitmapToLocal(60, filePath, rotationBitmap);

        if (originalFile.exists()) {
            originalFile.delete();
        }


        Bitmap smallBitmap = compress2SmallBitmap(requestWith, requestHeight, filePath, options);


        return smallBitmap;
    }

    /**
     * 保存bitmap 到本地
     *
     * @param quality  图片压缩质量
     * @param filePath 压缩后文件的存储路径
     * @param bitmap   需要压缩的bitmap
     * @throws IOException
     */
    private void saveBitmapToLocal(int quality, String filePath, Bitmap bitmap) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @return
     */
    private Bitmap rotationBitmap(Bitmap bitmap, String filePath) throws IOException {
        Bitmap rotationBitmap;
        if (TextUtils.isEmpty(filePath))
            return bitmap;

        //check the rotation of the image and display it properly
        ExifInterface exif;
        exif = new ExifInterface(filePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        //旋转图片
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        rotationBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null == rotationBitmap)
            rotationBitmap = bitmap;
        if (bitmap != rotationBitmap)
            bitmap.recycle();
        return rotationBitmap;
    }


    /**
     * 计算分辨率压缩比例
     *
     * @param options
     * @param requestWith
     * @param requestHeight
     * @return
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int requestWith, int requestHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        for (int i = 0; ; i++) {
            if (width >> i <= requestWith && height >> i <= requestHeight) {
                return (int) Math.pow(2.0D, i);
            }
        }
    }

    /**
     * 该算法使用的是鲁班的算法，如需查看详情请<a href = "https://github.com/Curzibn/Luban">点击查看</a>
     *
     * @param options
     * @return
     */
    private int computeSize(BitmapFactory.Options options) {
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;

        int longSide = Math.max(srcWidth, srcHeight);
        int shortSide = Math.min(srcWidth, srcHeight);

        float scale = ((float) shortSide / longSide);
        if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                return 1;
            } else if (longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280 == 0 ? 1 : longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }


    /**
     * 从URI获取文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    @SuppressLint("NewApi")
    public String getFilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * 判断外部存储是否可用
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public Bitmap compress2SmallBitmap(int requestWith, int requestHeight, String filePath, BitmapFactory.Options options) {
        /**return small image for display start**/
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // 计算压缩比例
        options.inSampleSize = calculateInSampleSize(options, requestWith, requestHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        /**return small image for display start**/
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * bitmap转为byte数组
     *
     * @param bmp         bitmap
     * @param needRecycle 是否回收（true：执行bitmap.recycle）
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 字节转换成File
     *
     * @param buf      image byte
     * @param fileName
     * @return file 路径
     */
    public static String byte2File(byte[] buf, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        String filePath = "";
        if (chekSD()) {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        File file = new File(filePath + File.separator + fileName);
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file.getAbsolutePath();
    }

    /**
     * 判断sd卡是否存在
     */
    public static boolean chekSD() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return true;
        }
        return false;
    }

}
