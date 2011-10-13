package com.htc.flashlight;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import java.io.Closeable;
import java.io.File;

public class Util
{
  private static final String TAG = "Util";
  public static final int UNCONSTRAINED = 255;

  public static float calcLagrange(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    float f1 = paramFloat7 - paramFloat3;
    float f2 = paramFloat7 - paramFloat5;
    float f3 = f1 * f2 * paramFloat2;
    float f4 = paramFloat1 - paramFloat3;
    float f5 = paramFloat1 - paramFloat5;
    float f6 = f4 * f5;
    float f7 = f3 / f6;
    float f8 = paramFloat7 - paramFloat1;
    float f9 = paramFloat7 - paramFloat5;
    float f10 = f8 * f9 * paramFloat4;
    float f11 = paramFloat3 - paramFloat1;
    float f12 = paramFloat3 - paramFloat5;
    float f13 = f11 * f12;
    float f14 = f10 / f13;
    float f15 = f7 + f14;
    float f16 = paramFloat7 - paramFloat1;
    float f17 = paramFloat7 - paramFloat3;
    float f18 = f16 * f17 * paramFloat6;
    float f19 = paramFloat5 - paramFloat1;
    float f20 = paramFloat5 - paramFloat3;
    float f21 = f19 * f20;
    float f22 = f18 / f21;
    return f15 + f22;
  }

  public static void closeSilently(Closeable paramCloseable)
  {
    if (paramCloseable == null)
      return;
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Throwable localThrowable)
    {
    }
  }

  private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    label33: int k;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
        break label74;
      j = 128;
      if (j >= i)
        break label114;
      k = i;
    }
    while (true)
    {
      return k;
      float f = d1 * d2;
      double d3 = paramInt2;
      i = (int)Math.ceil(Math.sqrt(f / d3));
      break;
      label74: double d4 = paramInt1;
      double d5 = Math.floor(d1 / d4);
      double d6 = paramInt1;
      double d7 = Math.floor(d2 / d6);
      j = (int)Math.min(d5, d7);
      break label33;
      label114: if ((paramInt2 == -1) && (paramInt1 == -1))
      {
        k = 1;
        continue;
      }
      if (paramInt1 == -1)
      {
        k = i;
        continue;
      }
      k = j;
    }
  }

  private static int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      j = 1;
      while (j < i)
        j <<= 1;
    }
    int j = (i + 7) / 8 * 8;
    return j;
  }

  public static void deleteFile(String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      if (localFile == null)
        return;
      if (!localFile.exists())
        return;
      boolean bool = localFile.delete();
      return;
    }
    catch (Exception localException)
    {
      String str = "deleteFile failed: " + paramString;
      int i = Log.e("Util", str);
    }
  }

  // ERROR //
  public static java.lang.Integer getIntegerFromFile(String paramString)
  {
    // Byte code:
    //   0: new 62	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 64	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_1
    //   9: aload_1
    //   10: ifnull +10 -> 20
    //   13: aload_1
    //   14: invokevirtual 68	java/io/File:exists	()Z
    //   17: ifne +36 -> 53
    //   20: new 73	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   27: ldc 94
    //   29: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: aload_0
    //   33: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore_2
    //   40: ldc 9
    //   42: aload_2
    //   43: invokestatic 90	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   46: istore_3
    //   47: aconst_null
    //   48: astore 4
    //   50: aload 4
    //   52: areturn
    //   53: new 96	java/io/FileReader
    //   56: dup
    //   57: aload_1
    //   58: invokespecial 99	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   61: astore 5
    //   63: new 101	java/io/BufferedReader
    //   66: dup
    //   67: aload 5
    //   69: iconst_1
    //   70: invokespecial 104	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   73: astore 6
    //   75: new 106	java/lang/StringBuffer
    //   78: dup
    //   79: invokespecial 107	java/lang/StringBuffer:<init>	()V
    //   82: astore 7
    //   84: aload 6
    //   86: invokevirtual 110	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   89: astore 8
    //   91: aload 7
    //   93: aload 8
    //   95: invokevirtual 113	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   98: astore 9
    //   100: aload 6
    //   102: invokevirtual 114	java/io/BufferedReader:close	()V
    //   105: aload 8
    //   107: ifnull +64 -> 171
    //   110: aload 8
    //   112: invokestatic 120	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   115: invokestatic 124	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   118: astore 4
    //   120: goto -70 -> 50
    //   123: astore 10
    //   125: aload 6
    //   127: invokevirtual 114	java/io/BufferedReader:close	()V
    //   130: aload 10
    //   132: athrow
    //   133: astore 11
    //   135: new 73	java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   142: ldc 126
    //   144: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: aload_0
    //   148: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   154: astore 12
    //   156: ldc 9
    //   158: aload 12
    //   160: invokestatic 90	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   163: istore 13
    //   165: aconst_null
    //   166: astore 4
    //   168: goto -118 -> 50
    //   171: aconst_null
    //   172: astore 4
    //   174: goto -124 -> 50
    //
    // Exception table:
    //   from	to	target	type
    //   84	100	123	finally
    //   53	84	133	java/lang/Exception
    //   100	133	133	java/lang/Exception
  }

  public static Bitmap makeBitmap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      int i = paramArrayOfByte.length;
      Bitmap localBitmap1 = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, i, localOptions);
      if ((localOptions.mCancel) || (localOptions.outWidth == -1) || (localOptions.outHeight == -1));
      Bitmap localBitmap2;
      for (localObject = null; ; localObject = localBitmap2)
      {
        return localObject;
        int j = computeSampleSize(localOptions, paramInt1, paramInt2);
        localOptions.inSampleSize = j;
        localOptions.inJustDecodeBounds = false;
        localOptions.inDither = false;
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        localOptions.inPreferredConfig = localConfig;
        int k = paramArrayOfByte.length;
        localBitmap2 = BitmapFactory.decodeByteArray(paramArrayOfByte, 0, k, localOptions);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        int m = Log.e("Util", "Got oom exception ", localOutOfMemoryError);
        Object localObject = null;
      }
    }
  }

  // ERROR //
  public static void saveBitmap(Bitmap paramBitmap, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 170	java/io/FileOutputStream
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 171	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   10: astore_3
    //   11: new 173	java/io/BufferedOutputStream
    //   14: dup
    //   15: aload_3
    //   16: invokespecial 176	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   19: astore 4
    //   21: aload_0
    //   22: ifnull +20 -> 42
    //   25: getstatic 182	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   28: astore 5
    //   30: aload_0
    //   31: aload 5
    //   33: bipush 100
    //   35: aload 4
    //   37: invokevirtual 188	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   40: istore 6
    //   42: aload 4
    //   44: invokevirtual 191	java/io/BufferedOutputStream:flush	()V
    //   47: aload 4
    //   49: ifnull +159 -> 208
    //   52: aload 4
    //   54: invokevirtual 192	java/io/BufferedOutputStream:close	()V
    //   57: aload 4
    //   59: astore 7
    //   61: return
    //   62: astore 8
    //   64: ldc 9
    //   66: ldc 194
    //   68: aload 8
    //   70: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   73: istore 9
    //   75: aload 4
    //   77: astore 10
    //   79: return
    //   80: astore 11
    //   82: ldc 9
    //   84: ldc 163
    //   86: aload 11
    //   88: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   91: istore 12
    //   93: aload_2
    //   94: ifnonnull +4 -> 98
    //   97: return
    //   98: aload_2
    //   99: invokevirtual 192	java/io/BufferedOutputStream:close	()V
    //   102: return
    //   103: astore 13
    //   105: ldc 9
    //   107: ldc 194
    //   109: aload 13
    //   111: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   114: istore 14
    //   116: return
    //   117: astore 11
    //   119: ldc 9
    //   121: ldc 196
    //   123: aload 11
    //   125: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   128: istore 15
    //   130: aload_2
    //   131: ifnonnull +4 -> 135
    //   134: return
    //   135: aload_2
    //   136: invokevirtual 192	java/io/BufferedOutputStream:close	()V
    //   139: return
    //   140: astore 16
    //   142: ldc 9
    //   144: ldc 194
    //   146: aload 16
    //   148: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   151: istore 17
    //   153: return
    //   154: astore 18
    //   156: aload_2
    //   157: ifnull +7 -> 164
    //   160: aload_2
    //   161: invokevirtual 192	java/io/BufferedOutputStream:close	()V
    //   164: aload 18
    //   166: athrow
    //   167: astore 19
    //   169: ldc 9
    //   171: ldc 194
    //   173: aload 19
    //   175: invokestatic 166	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   178: istore 20
    //   180: goto -16 -> 164
    //   183: astore 18
    //   185: aload 4
    //   187: astore 21
    //   189: goto -33 -> 156
    //   192: astore 11
    //   194: aload 4
    //   196: astore_2
    //   197: goto -78 -> 119
    //   200: astore 11
    //   202: aload 4
    //   204: astore_2
    //   205: goto -123 -> 82
    //   208: aload 4
    //   210: astore 22
    //   212: return
    //
    // Exception table:
    //   from	to	target	type
    //   52	57	62	java/lang/Exception
    //   2	21	80	java/lang/OutOfMemoryError
    //   98	102	103	java/lang/Exception
    //   2	21	117	java/lang/Exception
    //   135	139	140	java/lang/Exception
    //   2	21	154	finally
    //   82	93	154	finally
    //   119	130	154	finally
    //   160	164	167	java/lang/Exception
    //   25	47	183	finally
    //   25	47	192	java/lang/Exception
    //   25	47	200	java/lang/OutOfMemoryError
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.Util
 * JD-Core Version:    0.6.0
 */