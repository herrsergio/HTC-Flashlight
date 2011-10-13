package com.htc.flashlight;

import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FlashlightControler
{
  private static FlashlightControler Fcontrol = null;
  private static final String LED_LEVEL1 = "125";
  private static final String LED_LEVEL2 = "126";
  private static final String LED_LEVEL3 = "127";
  private static final String LED_NODE = "/sys/class/leds/flashlight/brightness";
  private static final String LED_OFF = "0";
  public static final int STATE_COUNT = 4;
  public static final int STATE_LEVEL1 = 1;
  public static final int STATE_LEVEL2 = 2;
  public static final int STATE_LEVEL3 = 3;
  public static final int STATE_OFF = 0;
  private static final String TAG = "FlashlightControler";
  private int mCurrentLevel;
  private boolean mPowerOn;
  private Method setFlashlightBrightness = null;
  private Object svc = null;

  private FlashlightControler()
  {
    reflectSetBrightness();
    this.mCurrentLevel = 1;
    this.mPowerOn = false;
  }

  public static FlashlightControler getInstance()
  {
    if (Fcontrol == null)
      Fcontrol = new FlashlightControler();
    return Fcontrol;
  }

  private void reflectSetBrightness()
  {
    try
    {
      Class localClass1 = Class.forName("android.os.ServiceManager");
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = String.class;
      Method localMethod1 = localClass1.getMethod("getService", arrayOfClass1);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = "htchardware";
      Object localObject1 = localMethod1.invoke(null, arrayOfObject1);
      Class localClass2 = Class.forName("android.os.IHtcHardwareService$Stub");
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = IBinder.class;
      Method localMethod2 = localClass2.getMethod("asInterface", arrayOfClass2);
      Object[] arrayOfObject2 = new Object[1];
      IBinder localIBinder = (IBinder)localObject1;
      arrayOfObject2[0] = localIBinder;
      Object localObject2 = localMethod2.invoke(null, arrayOfObject2);
      this.svc = localObject2;
      Class localClass3 = this.svc.getClass();
      Class[] arrayOfClass3 = new Class[1];
      Class localClass4 = Integer.TYPE;
      arrayOfClass3[0] = localClass4;
      Method localMethod3 = localClass3.getMethod("setFlashlightBrightness", arrayOfClass3);
      this.setFlashlightBrightness = localMethod3;
      int i = Log.d("FlashlightControler", "setLedLevelByFramework");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.setFlashlightBrightness = null;
      int j = Log.d("FlashlightControler", "setLedLevelByFile");
    }
  }

  private void setLedLevel(String paramString)
  {
    if (this.setFlashlightBrightness == null)
      setLedLevelByFile(paramString);
    while (true)
    {
      String str = "setLedLevel = " + paramString;
      int i = Log.d("[ATS][com.htc.flashlight][setLedLevel][successful]", str);
      return;
      setLedLevelByFramework(paramString);
    }
  }

  // ERROR //
  private void setLedLevelByFile(String paramString)
  {
    // Byte code:
    //   0: new 146	java/io/File
    //   3: dup
    //   4: ldc 20
    //   6: invokespecial 148	java/io/File:<init>	(Ljava/lang/String;)V
    //   9: astore_2
    //   10: aload_2
    //   11: ifnull +10 -> 21
    //   14: aload_2
    //   15: invokevirtual 152	java/io/File:exists	()Z
    //   18: ifne +12 -> 30
    //   21: ldc 37
    //   23: ldc 154
    //   25: invokestatic 116	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   28: istore_3
    //   29: return
    //   30: new 156	java/io/FileWriter
    //   33: dup
    //   34: aload_2
    //   35: invokespecial 159	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   38: astore 4
    //   40: new 161	java/io/BufferedWriter
    //   43: dup
    //   44: aload 4
    //   46: invokespecial 164	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   49: astore 5
    //   51: aload 5
    //   53: aload_1
    //   54: invokevirtual 167	java/io/BufferedWriter:write	(Ljava/lang/String;)V
    //   57: aload 5
    //   59: invokevirtual 170	java/io/BufferedWriter:close	()V
    //   62: return
    //   63: astore 6
    //   65: new 127	java/lang/StringBuilder
    //   68: dup
    //   69: invokespecial 128	java/lang/StringBuilder:<init>	()V
    //   72: ldc 172
    //   74: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: astore 7
    //   79: aload 6
    //   81: invokevirtual 173	java/io/IOException:toString	()Ljava/lang/String;
    //   84: astore 8
    //   86: aload 7
    //   88: aload 8
    //   90: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: invokevirtual 138	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   96: astore 9
    //   98: ldc 37
    //   100: aload 9
    //   102: invokestatic 116	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   105: istore 10
    //   107: aload 6
    //   109: invokevirtual 174	java/io/IOException:printStackTrace	()V
    //   112: return
    //   113: astore 11
    //   115: aload 5
    //   117: invokevirtual 170	java/io/BufferedWriter:close	()V
    //   120: aload 11
    //   122: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   30	51	63	java/io/IOException
    //   57	62	63	java/io/IOException
    //   115	123	63	java/io/IOException
    //   51	57	113	finally
  }

  private void setLedLevelByFramework(String paramString)
  {
    if (this.setFlashlightBrightness == null)
      return;
    try
    {
      Method localMethod = this.setFlashlightBrightness;
      Object localObject1 = this.svc;
      Object[] arrayOfObject = new Object[1];
      Integer localInteger = Integer.valueOf(paramString);
      arrayOfObject[0] = localInteger;
      Object localObject2 = localMethod.invoke(localObject1, arrayOfObject);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
  }

  private String transformtoLedLevel(int paramInt)
  {
    String str = "0";
    if (paramInt == 0)
      str = "0";
    while (true)
    {
      return str;
      if (paramInt == 1)
      {
        str = "125";
        continue;
      }
      if (paramInt == 2)
      {
        str = "126";
        continue;
      }
      if (paramInt != 3)
        continue;
      str = "127";
    }
  }

  private int transformtoStateLevel(String paramString)
  {
    int i = 0;
    if (paramString.equals("0"))
      i = 0;
    while (true)
    {
      return i;
      if (paramString.equals("125"))
      {
        i = 1;
        continue;
      }
      if (paramString.equals("126"))
      {
        i = 2;
        continue;
      }
      if (!paramString.equals("127"))
        continue;
      i = 3;
    }
  }

  public int GetHigherBrightness()
  {
    return (this.mCurrentLevel + 1) % 4;
  }

  public int GetLowerBrightness()
  {
    int i = this.mCurrentLevel + -1;
    if (i < 1)
      i = 1;
    return i;
  }

  public void PowerOff()
  {
    this.mPowerOn = false;
    String str = transformtoLedLevel(0);
    setLedLevel(str);
  }

  public void PowerOn()
  {
    this.mPowerOn = true;
    int i = this.mCurrentLevel;
    String str = transformtoLedLevel(i);
    setLedLevel(str);
  }

  public void adjustBrightness(int paramInt)
  {
    this.mCurrentLevel = paramInt;
    if (!isPowerOn())
      return;
    int i = this.mCurrentLevel;
    String str = transformtoLedLevel(i);
    setLedLevel(str);
  }

  public int getCurrentBrightness()
  {
    return this.mCurrentLevel;
  }

  public boolean isPowerOn()
  {
    return this.mPowerOn;
  }

  public void switchPower()
  {
    if (isPowerOn())
    {
      this.mPowerOn = false;
      String str1 = transformtoLedLevel(0);
      setLedLevel(str1);
      return;
    }
    this.mPowerOn = true;
    int i = this.mCurrentLevel;
    String str2 = transformtoLedLevel(i);
    setLedLevel(str2);
  }

  public boolean turnDownBrightness()
  {
    int i = this.mCurrentLevel - 1;
    this.mCurrentLevel = i;
    if (this.mCurrentLevel < 1)
      this.mCurrentLevel = 1;
    for (int j = 0; ; j = 1)
    {
      return j;
      if (!isPowerOn())
        continue;
      int k = this.mCurrentLevel;
      String str = transformtoLedLevel(k);
      setLedLevel(str);
    }
  }

  public boolean turnUpBrightness()
  {
    int i = this.mCurrentLevel + 1;
    this.mCurrentLevel = i;
    if (this.mCurrentLevel > 3)
      this.mCurrentLevel = 3;
    for (int j = 0; ; j = 1)
    {
      return j;
      if (!isPowerOn())
        continue;
      int k = this.mCurrentLevel;
      String str = transformtoLedLevel(k);
      setLedLevel(str);
    }
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.FlashlightControler
 * JD-Core Version:    0.6.0
 */