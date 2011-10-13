package com.htc.flashlight;

import android.os.FileObserver;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FlashRestriction
{
  private static final byte BATTERY_CAPACITY_FLAG = 1;
  private static int BATTERY_CAPACITY_LIMIT = 0;
  private static final String BATTERY_CAPACITY_LIMIT_PATH = "/sys/camera_led_status/low_cap_limit";
  private static final String BATTERY_CAPACITY_PATH = "/sys/class/power_supply/battery/capacity";
  private static final byte BATTERY_TEMPERATURE_FLAG = 2;
  private static int BATTERY_TEMPERATURE_LIMIT = 0;
  private static final String BATTERY_TEMPERATURE_LIMIT_PATH = "/sys/camera_led_status/low_temp_limit";
  private static final String BATTERY_TEMPERATURE_PATH = "/sys/class/power_supply/battery/batt_temp";
  private static final byte HOTSPOT_STATUS_FLAG = 16;
  private static final String HOTSPOT_STATUS_PATH = "/sys/camera_led_status/led_hotspot_status";
  private static final int MSGID_UPDATE_FLASH_FROM_RESTRICTION = 9;
  private static final byte NO_LIMIT_FLAG = 0;
  private static final byte RIL_STATUS_FLAG = 8;
  private static final String RIL_STATUS_PATH = "/sys/camera_led_status/led_ril_status";
  private static final String TAG = "FlashRestriction";
  private static final byte WIMAX_STATUS_FLAG = 4;
  private static final String WIMAX_STATUS_PATH = "/sys/camera_led_status/led_wimax_status";
  private byte mDisableFlash = 0;
  private FileObserver mFileObserver_BatCap = null;
  private FileObserver mFileObserver_BatTemp = null;
  private FileObserver mFileObserver_HotSpot = null;
  private FileObserver mFileObserver_RIL = null;
  private FileObserver mFileObserver_Wimax = null;
  private Handler mHandler = null;
  private byte mIsLimitBatCap = 0;
  private byte mIsLimitBatTemp = 0;
  private byte mIsLimitHotSpot = 0;
  private byte mIsLimitRIL = 0;
  private byte mIsLimitWimax = 0;

  static
  {
    BATTERY_CAPACITY_LIMIT = 15;
    initBatteryLimit();
  }

  public FlashRestriction(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  private void checkBatteryCapacity()
  {
    if (this.mIsLimitBatCap == 1);
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = false)
    {
      int i = BATTERY_CAPACITY_LIMIT;
      bool2 = isLower("/sys/class/power_supply/battery/capacity", i);
      if (bool2 != bool1)
        break;
      return;
    }
    byte b1 = (byte)(this.mIsLimitBatCap ^ 0x1);
    this.mIsLimitBatCap = b1;
    boolean bool3;
    if (this.mDisableFlash != null)
    {
      bool3 = true;
      int j = this.mIsLimitHotSpot;
      int k = this.mIsLimitRIL;
      int m = j | k;
      int n = this.mIsLimitWimax;
      int i1 = m | n;
      int i2 = this.mIsLimitBatTemp;
      int i3 = i1 | i2;
      int i4 = this.mIsLimitBatCap;
      byte b2 = (byte)(i3 | i4);
      this.mDisableFlash = b2;
      if (bool2 == bool3)
        return;
      if (bool2 != true)
        break label155;
      int i5 = Log.w("FlashRestriction", "battery capacity lower, must disable flashlight");
    }
    while (true)
    {
      updateFlashState();
      return;
      bool3 = false;
      break;
      label155: int i6 = Log.w("FlashRestriction", "battery capacity not lower, recover flashlight");
    }
  }

  private void checkBatteryTemperature()
  {
    if (this.mIsLimitBatTemp == 2);
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = false)
    {
      int i = BATTERY_TEMPERATURE_LIMIT;
      bool2 = isLower("/sys/class/power_supply/battery/batt_temp", i);
      if (bool2 != bool1)
        break;
      return;
    }
    byte b1 = (byte)(this.mIsLimitBatTemp ^ 0x2);
    this.mIsLimitBatTemp = b1;
    boolean bool3;
    if (this.mDisableFlash != null)
    {
      bool3 = true;
      int j = this.mIsLimitHotSpot;
      int k = this.mIsLimitRIL;
      int m = j | k;
      int n = this.mIsLimitWimax;
      int i1 = m | n;
      int i2 = this.mIsLimitBatTemp;
      int i3 = i1 | i2;
      int i4 = this.mIsLimitBatCap;
      byte b2 = (byte)(i3 | i4);
      this.mDisableFlash = b2;
      if (bool2 == bool3)
        return;
      if (bool2 != true)
        break label155;
      int i5 = Log.w("FlashRestriction", "battery temperature lower, must disable flashlight");
    }
    while (true)
    {
      updateFlashState();
      return;
      bool3 = false;
      break;
      label155: int i6 = Log.w("FlashRestriction", "battery temperature not lower, recover flashlight");
    }
  }

  private void checkHotSpotStatus()
  {
    if (this.mIsLimitHotSpot == 16);
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = false)
    {
      bool2 = isChecked("/sys/camera_led_status/led_hotspot_status");
      if (bool2 != bool1)
        break;
      return;
    }
    byte b1 = (byte)(this.mIsLimitHotSpot ^ 0x10);
    this.mIsLimitHotSpot = b1;
    boolean bool3;
    if (this.mDisableFlash != null)
    {
      bool3 = true;
      int i = this.mIsLimitHotSpot;
      int j = this.mIsLimitRIL;
      int k = i | j;
      int m = this.mIsLimitWimax;
      int n = k | m;
      int i1 = this.mIsLimitBatTemp;
      int i2 = n | i1;
      int i3 = this.mIsLimitBatCap;
      byte b2 = (byte)(i2 | i3);
      this.mDisableFlash = b2;
      if (bool2 == bool3)
        return;
      if (bool2 != true)
        break label150;
      int i4 = Log.v("FlashRestriction", "enable hot spot, must disable flashlight");
    }
    while (true)
    {
      updateFlashState();
      return;
      bool3 = false;
      break;
      label150: int i5 = Log.v("FlashRestriction", "disable hot spot, restore flashlight");
    }
  }

  private void checkRILStatus()
  {
    if (this.mIsLimitRIL == 8);
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = false)
    {
      bool2 = isChecked("/sys/camera_led_status/led_ril_status");
      int i = Log.v("FlashRestriction", "checkRILStatus()");
      if (bool2 != bool1)
        break;
      return;
    }
    byte b1 = (byte)(this.mIsLimitRIL ^ 0x8);
    this.mIsLimitRIL = b1;
    boolean bool3;
    if (this.mDisableFlash != null)
    {
      bool3 = true;
      int j = this.mIsLimitHotSpot;
      int k = this.mIsLimitRIL;
      int m = j | k;
      int n = this.mIsLimitWimax;
      int i1 = m | n;
      int i2 = this.mIsLimitBatTemp;
      int i3 = i1 | i2;
      int i4 = this.mIsLimitBatCap;
      byte b2 = (byte)(i3 | i4);
      this.mDisableFlash = b2;
      if (bool2 == bool3)
        return;
      if (bool2 != true)
        break label160;
      int i5 = Log.w("FlashRestriction", "phone incoming, must disable flashlight");
    }
    while (true)
    {
      updateFlashState();
      return;
      bool3 = false;
      break;
      label160: int i6 = Log.w("FlashRestriction", "no phone incoming, restore flashlight");
    }
  }

  private void checkWimaxStatus()
  {
    if (this.mIsLimitWimax == 4);
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = false)
    {
      bool2 = isChecked("/sys/camera_led_status/led_wimax_status");
      if (bool2 != bool1)
        break;
      return;
    }
    byte b1 = (byte)(this.mIsLimitWimax ^ 0x4);
    this.mIsLimitWimax = b1;
    boolean bool3;
    if (this.mDisableFlash != null)
    {
      bool3 = true;
      int i = this.mIsLimitHotSpot;
      int j = this.mIsLimitRIL;
      int k = i | j;
      int m = this.mIsLimitWimax;
      int n = k | m;
      int i1 = this.mIsLimitBatTemp;
      int i2 = n | i1;
      int i3 = this.mIsLimitBatCap;
      byte b2 = (byte)(i2 | i3);
      this.mDisableFlash = b2;
      if (bool2 == bool3)
        return;
      if (bool2 != true)
        break label148;
      int i4 = Log.w("FlashRestriction", "use wimax, must disable flashlight");
    }
    while (true)
    {
      updateFlashState();
      return;
      bool3 = false;
      break;
      label148: int i5 = Log.w("FlashRestriction", "no use wimax, recover flashlight");
    }
  }

  private static void initBatteryLimit()
  {
    Integer localInteger1 = Util.getIntegerFromFile("/sys/camera_led_status/low_temp_limit");
    if (localInteger1 != null)
    {
      if ((localInteger1.intValue() > 65436) && (localInteger1.intValue() < 100))
        BATTERY_TEMPERATURE_LIMIT = localInteger1.intValue() * 10;
      StringBuilder localStringBuilder1 = new StringBuilder().append("new Battery Temp limit: ");
      int i = BATTERY_TEMPERATURE_LIMIT;
      String str1 = i;
      int j = Log.v("FlashRestriction", str1);
    }
    while (true)
    {
      Integer localInteger2 = Util.getIntegerFromFile("/sys/camera_led_status/low_cap_limit");
      if (localInteger2 == null)
        break;
      BATTERY_CAPACITY_LIMIT = localInteger2.intValue();
      StringBuilder localStringBuilder2 = new StringBuilder().append("new Battery Capacity limit: ");
      int k = BATTERY_CAPACITY_LIMIT;
      String str2 = k;
      int m = Log.v("FlashRestriction", str2);
      return;
      StringBuilder localStringBuilder3 = new StringBuilder().append("default Battery Temp limit: ");
      int n = BATTERY_TEMPERATURE_LIMIT;
      String str3 = n;
      int i1 = Log.v("FlashRestriction", str3);
    }
    StringBuilder localStringBuilder4 = new StringBuilder().append("default Battery Capacity limit: ");
    int i2 = BATTERY_CAPACITY_LIMIT;
    String str4 = i2;
    int i3 = Log.v("FlashRestriction", str4);
  }

  private void initDisableFlash()
  {
    this.mIsLimitHotSpot = 0;
    this.mIsLimitRIL = 0;
    this.mIsLimitWimax = 0;
    this.mIsLimitBatTemp = 0;
    this.mIsLimitBatCap = 0;
    if (isChecked("/sys/camera_led_status/led_hotspot_status") == true)
    {
      this.mIsLimitHotSpot = 16;
      int i = Log.v("FlashRestriction", "enable hot spot, must disable flashlight");
    }
    if (isChecked("/sys/camera_led_status/led_ril_status") == true)
    {
      this.mIsLimitRIL = 8;
      int j = Log.v("FlashRestriction", "phone incoming, must disable flashlight");
    }
    if (isChecked("/sys/camera_led_status/led_wimax_status") == true)
    {
      this.mIsLimitWimax = 4;
      int k = Log.v("FlashRestriction", "use wimax, must disable flashlight");
    }
    int m = BATTERY_TEMPERATURE_LIMIT;
    if (isLower("/sys/class/power_supply/battery/batt_temp", m) == true)
    {
      this.mIsLimitBatTemp = 2;
      int n = Log.v("FlashRestriction", "battery temperature lower, must disable flashlight");
    }
    int i1 = BATTERY_CAPACITY_LIMIT;
    if (isLower("/sys/class/power_supply/battery/capacity", i1) == true)
    {
      this.mIsLimitBatCap = 1;
      int i2 = Log.v("FlashRestriction", "battery capacity lower, must disable flashlight");
    }
    int i3 = this.mIsLimitHotSpot;
    int i4 = this.mIsLimitRIL;
    int i5 = i3 | i4;
    int i6 = this.mIsLimitWimax;
    int i7 = i5 | i6;
    int i8 = this.mIsLimitBatTemp;
    int i9 = i7 | i8;
    int i10 = this.mIsLimitBatCap;
    byte b = (byte)(i9 | i10);
    this.mDisableFlash = b;
    if (isDisableFlash() != true)
      return;
    updateFlashState();
  }

  // ERROR //
  private boolean isChecked(String paramString)
  {
    // Byte code:
    //   0: new 216	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 219	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: aload_2
    //   10: ifnull +10 -> 20
    //   13: aload_2
    //   14: invokevirtual 222	java/io/File:exists	()Z
    //   17: ifne +37 -> 54
    //   20: new 187	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   27: ldc 224
    //   29: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: aload_1
    //   33: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore_3
    //   40: ldc 53
    //   42: aload_3
    //   43: invokestatic 160	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   46: istore 4
    //   48: iconst_0
    //   49: istore 5
    //   51: iload 5
    //   53: ireturn
    //   54: iconst_0
    //   55: istore 6
    //   57: new 226	java/io/FileReader
    //   60: dup
    //   61: aload_2
    //   62: invokespecial 229	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   65: astore 7
    //   67: new 231	java/io/BufferedReader
    //   70: dup
    //   71: aload 7
    //   73: iconst_1
    //   74: invokespecial 234	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   77: astore 8
    //   79: aload 8
    //   81: invokevirtual 237	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   84: astore 9
    //   86: aload 9
    //   88: astore 10
    //   90: aload 8
    //   92: invokevirtual 240	java/io/BufferedReader:close	()V
    //   95: aload 10
    //   97: ifnull +78 -> 175
    //   100: aload 10
    //   102: iconst_0
    //   103: invokevirtual 246	java/lang/String:charAt	(I)C
    //   106: bipush 49
    //   108: if_icmpne +67 -> 175
    //   111: iconst_1
    //   112: istore 6
    //   114: ldc 53
    //   116: ldc 248
    //   118: invokestatic 160	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   121: istore 11
    //   123: iload 6
    //   125: istore 5
    //   127: goto -76 -> 51
    //   130: astore 12
    //   132: aload 8
    //   134: invokevirtual 240	java/io/BufferedReader:close	()V
    //   137: aload 12
    //   139: athrow
    //   140: astore 13
    //   142: new 187	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   149: ldc 250
    //   151: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: aload_1
    //   155: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: astore 14
    //   163: ldc 53
    //   165: aload 14
    //   167: invokestatic 160	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   170: istore 15
    //   172: goto -49 -> 123
    //   175: new 187	java/lang/StringBuilder
    //   178: dup
    //   179: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   182: ldc 252
    //   184: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: aload_1
    //   188: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   194: astore 16
    //   196: ldc 53
    //   198: aload 16
    //   200: invokestatic 160	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   203: istore 17
    //   205: goto -82 -> 123
    //
    // Exception table:
    //   from	to	target	type
    //   79	86	130	finally
    //   57	79	140	java/lang/Exception
    //   90	140	140	java/lang/Exception
    //   175	205	140	java/lang/Exception
  }

  // ERROR //
  private boolean isLower(String paramString, int paramInt)
  {
    // Byte code:
    //   0: new 216	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 219	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ifnull +10 -> 20
    //   13: aload_3
    //   14: invokevirtual 222	java/io/File:exists	()Z
    //   17: ifne +39 -> 56
    //   20: new 187	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   27: ldc 254
    //   29: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: aload_1
    //   33: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore 4
    //   41: ldc 53
    //   43: aload 4
    //   45: invokestatic 142	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   48: istore 5
    //   50: iconst_0
    //   51: istore 6
    //   53: iload 6
    //   55: ireturn
    //   56: iconst_0
    //   57: istore 7
    //   59: new 226	java/io/FileReader
    //   62: dup
    //   63: aload_3
    //   64: invokespecial 229	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   67: astore 8
    //   69: new 231	java/io/BufferedReader
    //   72: dup
    //   73: aload 8
    //   75: iconst_1
    //   76: invokespecial 234	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   79: astore 9
    //   81: aload 9
    //   83: invokevirtual 237	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   86: astore 10
    //   88: aload 10
    //   90: astore 11
    //   92: aload 9
    //   94: invokevirtual 240	java/io/BufferedReader:close	()V
    //   97: aload 11
    //   99: ifnull +109 -> 208
    //   102: aload 11
    //   104: invokestatic 258	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   107: istore 12
    //   109: iload 12
    //   111: iload_2
    //   112: if_icmpge +6 -> 118
    //   115: iconst_1
    //   116: istore 7
    //   118: iload 7
    //   120: istore 6
    //   122: goto -69 -> 53
    //   125: astore 13
    //   127: aload 9
    //   129: invokevirtual 240	java/io/BufferedReader:close	()V
    //   132: aload 13
    //   134: athrow
    //   135: astore 14
    //   137: new 187	java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   144: ldc_w 260
    //   147: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: aload_1
    //   151: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   157: astore 15
    //   159: ldc 53
    //   161: aload 15
    //   163: invokestatic 263	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   166: istore 16
    //   168: goto -50 -> 118
    //   171: astore 17
    //   173: new 187	java/lang/StringBuilder
    //   176: dup
    //   177: invokespecial 188	java/lang/StringBuilder:<init>	()V
    //   180: ldc_w 265
    //   183: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: aload 11
    //   188: invokevirtual 194	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   194: astore 18
    //   196: ldc 53
    //   198: aload 18
    //   200: invokestatic 263	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   203: istore 19
    //   205: goto -87 -> 118
    //   208: ldc 53
    //   210: ldc_w 267
    //   213: invokestatic 263	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   216: istore 20
    //   218: goto -100 -> 118
    //
    // Exception table:
    //   from	to	target	type
    //   81	88	125	finally
    //   59	81	135	java/lang/Exception
    //   92	97	135	java/lang/Exception
    //   127	135	135	java/lang/Exception
    //   173	218	135	java/lang/Exception
    //   102	109	171	java/lang/Exception
  }

  private void startFileObserver()
  {
    int i = Log.v("FlashRestriction", "startFileObserver() - start");
    try
    {
      if (this.mFileObserver_HotSpot == null)
      {
        1 local1 = new FileObserver("/sys/camera_led_status/led_hotspot_status", 4095)
        {
          public void onEvent(int paramInt, String paramString)
          {
            if ((paramInt & 0x2) == 0)
              return;
            int i = Log.v("FlashRestriction", "hot spot status - MODIFY");
            FlashRestriction.this.checkHotSpotStatus();
          }
        };
        this.mFileObserver_HotSpot = local1;
      }
      this.mFileObserver_HotSpot.startWatching();
    }
    catch (Exception localException4)
    {
      try
      {
        if (this.mFileObserver_RIL == null)
        {
          2 local2 = new FileObserver("/sys/camera_led_status/led_ril_status", 4095)
          {
            public void onEvent(int paramInt, String paramString)
            {
              if ((paramInt & 0x2) == 0)
                return;
              int i = Log.e("FlashRestriction", "phone status - MODIFY");
              FlashRestriction.this.checkRILStatus();
            }
          };
          this.mFileObserver_RIL = local2;
        }
        this.mFileObserver_RIL.startWatching();
      }
      catch (Exception localException4)
      {
        try
        {
          if (this.mFileObserver_Wimax == null)
          {
            3 local3 = new FileObserver("/sys/camera_led_status/led_wimax_status", 4095)
            {
              public void onEvent(int paramInt, String paramString)
              {
                if ((paramInt & 0x2) == 0)
                  return;
                int i = Log.v("FlashRestriction", "wimax status - MODIFY");
                FlashRestriction.this.checkWimaxStatus();
              }
            };
            this.mFileObserver_Wimax = local3;
          }
          this.mFileObserver_Wimax.startWatching();
        }
        catch (Exception localException4)
        {
          try
          {
            if (this.mFileObserver_BatTemp == null)
            {
              4 local4 = new FileObserver("/sys/class/power_supply/battery/batt_temp", 4095)
              {
                public void onEvent(int paramInt, String paramString)
                {
                  if ((paramInt & 0x2) == 0)
                    return;
                  int i = Log.v("FlashRestriction", "battery temperature - MODIFY");
                  FlashRestriction.this.checkBatteryTemperature();
                }
              };
              this.mFileObserver_BatTemp = local4;
            }
            this.mFileObserver_BatTemp.startWatching();
          }
          catch (Exception localException4)
          {
            try
            {
              while (true)
              {
                if (this.mFileObserver_BatCap == null)
                {
                  5 local5 = new FileObserver("/sys/class/power_supply/battery/capacity", 4095)
                  {
                    public void onEvent(int paramInt, String paramString)
                    {
                      if ((paramInt & 0x2) == 0)
                        return;
                      int i = Log.v("FlashRestriction", "battery capacity - MODIFY");
                      FlashRestriction.this.checkBatteryCapacity();
                    }
                  };
                  this.mFileObserver_BatCap = local5;
                }
                this.mFileObserver_BatCap.startWatching();
                int j = Log.v("FlashRestriction", "startFileObserver() - end");
                return;
                localException1 = localException1;
                int k = Log.e("FlashRestriction", "mFileObserver_HotSpot.startWatching() failed!!", localException1);
                continue;
                localException2 = localException2;
                int m = Log.e("FlashRestriction", "mFileObserver_RIL.startWatching() failed!!", localException2);
                continue;
                localException3 = localException3;
                int n = Log.e("FlashRestriction", "mFileObserver_Wimax.startWatching() failed!!", localException3);
                continue;
                localException4 = localException4;
                int i1 = Log.e("FlashRestriction", "mFileObserver_BatTemp.startWatching() failed!!", localException4);
              }
            }
            catch (Exception localException5)
            {
              while (true)
                int i2 = Log.e("FlashRestriction", "mFileObserver_BatCap.startWatching() failed!!", localException5);
            }
          }
        }
      }
    }
  }

  private void stopFileObserver()
  {
    int i = Log.v("FlashRestriction", "stopFileObserver() - start");
    if (this.mFileObserver_HotSpot != null);
    try
    {
      this.mFileObserver_HotSpot.stopWatching();
      if (this.mFileObserver_RIL == null);
    }
    catch (Exception localException4)
    {
      try
      {
        this.mFileObserver_RIL.stopWatching();
        if (this.mFileObserver_Wimax == null);
      }
      catch (Exception localException4)
      {
        try
        {
          this.mFileObserver_Wimax.stopWatching();
          if (this.mFileObserver_BatTemp == null);
        }
        catch (Exception localException4)
        {
          try
          {
            this.mFileObserver_BatTemp.stopWatching();
            if (this.mFileObserver_BatCap == null);
          }
          catch (Exception localException4)
          {
            try
            {
              while (true)
              {
                this.mFileObserver_BatCap.stopWatching();
                int j = Log.v("FlashRestriction", "stopFileObserver() - end");
                return;
                localException1 = localException1;
                int k = Log.e("FlashRestriction", "mFileObserver_HotSpot.stopWatching() failed!!", localException1);
                continue;
                localException2 = localException2;
                int m = Log.e("FlashRestriction", "mFileObserver_RIL.stopWatching() failed!!", localException2);
                continue;
                localException3 = localException3;
                int n = Log.e("FlashRestriction", "mFileObserver_Wimax.stopWatching() failed!!", localException3);
              }
              localException4 = localException4;
              int i1 = Log.e("FlashRestriction", "mFileObserver_BatTemp.stopWatching() failed!!", localException4);
            }
            catch (Exception localException5)
            {
              while (true)
                int i2 = Log.e("FlashRestriction", "mFileObserver_BatCap.stopWatching() failed!!", localException5);
            }
          }
        }
      }
    }
  }

  private void updateFlashState()
  {
    Logger.print("updateFlashState()");
    Handler localHandler = this.mHandler;
    Message localMessage = this.mHandler.obtainMessage(9);
    if (localHandler.sendMessage(localMessage))
      return;
    Logger.print("updateFlashState !sendMessage MSGID_UPDATE_FLASH_FROM_RESTRICTION");
  }

  public int getRestrictionHint()
  {
    int i = 2130968583;
    int j = this.mDisableFlash;
    int k = this.mIsLimitBatTemp;
    if ((j & k) > 0)
      i = 2130968585;
    while (true)
    {
      return i;
      int m = this.mDisableFlash;
      int n = this.mIsLimitBatCap;
      if ((m & n) > 0)
      {
        i = 2130968584;
        continue;
      }
      int i1 = this.mDisableFlash;
      int i2 = this.mIsLimitHotSpot;
      if ((i1 & i2) > 0)
      {
        i = 2130968587;
        continue;
      }
      int i3 = this.mDisableFlash;
      int i4 = this.mIsLimitRIL;
      if ((i3 & i4) <= 0)
        continue;
      i = 2130968586;
    }
  }

  public void initFlashRestriction()
  {
    initDisableFlash();
    startFileObserver();
  }

  public boolean isDisableFlash()
  {
    if (this.mDisableFlash != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  public void releaseFlashRestriction()
  {
    stopFileObserver();
    this.mHandler = null;
    this.mFileObserver_HotSpot = null;
    this.mFileObserver_RIL = null;
    this.mFileObserver_Wimax = null;
    this.mFileObserver_BatTemp = null;
    this.mFileObserver_BatCap = null;
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.FlashRestriction
 * JD-Core Version:    0.6.0
 */