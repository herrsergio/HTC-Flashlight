package com.htc.flashlight;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FlashlightActivity extends Activity
{
  protected static final int MENU_AUTOFLASH = 1;
  protected static final int MENU_AUTOFLASH_OFF = 2;
  protected static final int MENU_MANUAL = 5;
  protected static final int MENU_SOS = 3;
  protected static final int MENU_SOS_OFF = 4;
  private static final int MSGID_BATT_ICON_HIDE = 5;
  private static final int MSGID_BATT_ICON_SHOW = 4;
  private static final int MSGID_CLOSE_TIPS = 8;
  private static final int MSGID_FLASH_OFF = 7;
  private static final int MSGID_FLASH_ON = 6;
  private static final int MSGID_TIME_UP = 2;
  private static final int MSGID_UPDATE_FLASH_FROM_RESTRICTION = 9;
  private static final int MSGID_UPDATE_UI = 1;
  private static final int TIME_UP_INTERVAL = 600000;
  private FlashlightControler mControler = null;
  int mDownX = -1;
  int mDownY = -1;
  FlashlightBattery mFLBattery;
  FlashlightTwinkle mFLTwinkle;
  private FlashRestriction mFlashRestriction = null;
  FLMsgHandler mHandler;
  private ImageView mMain = null;
  private ImageView mMode_auto = null;
  private ImageView mMode_sos = null;
  private View mNotice_bg = null;
  private TextView mNotice_text = null;
  private ImageView mPower = null;
  private PowerManager mPowerMgr;
  private PowerManager.WakeLock mPowerWakeLock;
  Rect mRectPower = null;
  private ImageView mStatus = null;
  private Toast mToast = null;
  private boolean m_bHintOnOff = false;

  public FlashlightActivity()
  {
    FLMsgHandler localFLMsgHandler = new FLMsgHandler();
    this.mHandler = localFLMsgHandler;
    this.mFLBattery = null;
    this.mFLTwinkle = null;
  }

  private void CloseTwinkle()
  {
    this.mFLTwinkle.FlashSOS(false);
    this.mFLTwinkle.autoFlashSwitch(false);
    this.mControler.adjustBrightness(0);
    this.mHandler.removeMessages(2);
    this.mPowerWakeLock.release();
    UpdateCurrentModeIcon();
  }

  private boolean InitUI()
  {
    boolean bool = getPreferences(0).getBoolean("Falshlight_showHint", true);
    this.m_bHintOnOff = bool;
    setContentView(2130903040);
    View localView1 = findViewById(2131099652);
    this.mNotice_bg = localView1;
    TextView localTextView1 = (TextView)findViewById(2131099653);
    this.mNotice_text = localTextView1;
    int i;
    if ((this.mNotice_bg == null) || (this.mNotice_text == null))
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("[InitUI]mNotice_bg = ");
      View localView2 = this.mNotice_bg;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localView2).append(", ").append("mNotice_text = ");
      TextView localTextView2 = this.mNotice_text;
      Logger.print(localTextView2);
      i = 0;
    }
    while (true)
    {
      return i;
      if (!this.m_bHintOnOff)
      {
        this.mNotice_bg.setVisibility(8);
        this.mNotice_text.setVisibility(8);
      }
      ImageView localImageView1 = (ImageView)findViewById(2131099650);
      this.mStatus = localImageView1;
      ImageView localImageView2 = (ImageView)findViewById(2131099651);
      this.mPower = localImageView2;
      ImageView localImageView3 = (ImageView)findViewById(2131099649);
      this.mMain = localImageView3;
      ImageView localImageView4 = (ImageView)findViewById(2131099654);
      this.mMode_sos = localImageView4;
      ImageView localImageView5 = (ImageView)findViewById(2131099655);
      this.mMode_auto = localImageView5;
      if ((this.mStatus == null) || (this.mPower == null) || (this.mMain == null))
      {
        StringBuilder localStringBuilder3 = new StringBuilder().append("[InitUI]mStatus = ");
        ImageView localImageView6 = this.mStatus;
        StringBuilder localStringBuilder4 = localStringBuilder3.append(localImageView6).append(", ").append("mPower = ");
        ImageView localImageView7 = this.mPower;
        StringBuilder localStringBuilder5 = localStringBuilder4.append(localImageView7).append(", ").append("mMain = ");
        ImageView localImageView8 = this.mMain;
        Logger.print(localImageView8);
        i = 0;
        continue;
      }
      ImageView localImageView9 = this.mPower;
      FLClickListener localFLClickListener = new FLClickListener();
      localImageView9.setOnClickListener(localFLClickListener);
      Toast localToast = Toast.makeText(this, "", 1);
      this.mToast = localToast;
      i = 1;
    }
  }

  private void SwitchPower()
  {
    if ((this.mControler == null) || (this.mHandler == null) || (this.mPowerWakeLock == null))
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("SwitchPower()mControler = ");
      FlashlightControler localFlashlightControler = this.mControler;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localFlashlightControler).append(", ").append("mHandler = ");
      FLMsgHandler localFLMsgHandler = this.mHandler;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localFLMsgHandler).append(", ").append("mPowerWakeLock = ");
      PowerManager.WakeLock localWakeLock = this.mPowerWakeLock;
      Logger.print(localWakeLock);
      return;
    }
    if (this.mControler.isPowerOn())
    {
      this.mHandler.removeMessages(2);
      this.mPowerWakeLock.release();
    }
    while (true)
    {
      this.mControler.switchPower();
      return;
      Message localMessage = this.mHandler.obtainMessage(2);
      if (!this.mHandler.sendMessageDelayed(localMessage, 600000L))
        Logger.print("SwitchPower() !sendMessageDelayed MSGID_TIME_UP");
      this.mPowerWakeLock.acquire();
    }
  }

  private void UpdateCurrentLight()
  {
    if (this.mControler.getCurrentBrightness() == 1)
    {
      this.mMain.setImageResource(2130837506);
      return;
    }
    if (this.mControler.getCurrentBrightness() == 2)
    {
      this.mMain.setImageResource(2130837507);
      return;
    }
    if (this.mControler.getCurrentBrightness() == 3)
    {
      this.mMain.setImageResource(2130837508);
      return;
    }
    if (this.mControler.getCurrentBrightness() != 0)
      return;
    this.mMain.setImageResource(2130837505);
  }

  private void UpdateCurrentModeIcon()
  {
    if (this.mFLTwinkle == null)
      return;
    if (this.mFLTwinkle.isSOS())
    {
      this.mMode_sos.setVisibility(0);
      this.mMode_sos.setImageResource(2130837509);
      this.mMode_auto.setVisibility(8);
      return;
    }
    if (this.mFLTwinkle.isAutoFlash())
    {
      this.mMode_auto.setVisibility(0);
      this.mMode_auto.setImageResource(2130837504);
      this.mMode_sos.setVisibility(8);
      return;
    }
    this.mMode_sos.setVisibility(8);
    this.mMode_auto.setVisibility(8);
  }

  private void closeHint()
  {
    this.m_bHintOnOff = false;
    SharedPreferences.Editor localEditor1 = getPreferences(0).edit();
    boolean bool1 = this.m_bHintOnOff;
    SharedPreferences.Editor localEditor2 = localEditor1.putBoolean("Falshlight_showHint", bool1);
    boolean bool2 = localEditor1.commit();
    Message localMessage = this.mHandler.obtainMessage(8);
    if (this.mHandler.sendMessage(localMessage))
      return;
    Logger.print("onClick !sendMessage MSGID_CLOSE_TIPS");
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!requestWindowFeature(1))
    {
      Logger.print("!Set to full screen");
      finish();
      return;
    }
    getWindow().setFlags(1024, 1024);
    if (!InitUI())
    {
      Logger.print("!init UI");
      finish();
      return;
    }
    FlashlightControler localFlashlightControler = FlashlightControler.getInstance();
    this.mControler = localFlashlightControler;
    this.mControler.PowerOff();
    FLMsgHandler localFLMsgHandler1 = this.mHandler;
    FlashlightBattery localFlashlightBattery = new FlashlightBattery(localFLMsgHandler1);
    this.mFLBattery = localFlashlightBattery;
    FLMsgHandler localFLMsgHandler2 = this.mHandler;
    FlashlightTwinkle localFlashlightTwinkle = new FlashlightTwinkle(localFLMsgHandler2);
    this.mFLTwinkle = localFlashlightTwinkle;
    PowerManager localPowerManager1 = (PowerManager)getSystemService("power");
    this.mPowerMgr = localPowerManager1;
    PowerManager.WakeLock localWakeLock1 = this.mPowerMgr.newWakeLock(10, "Falshlight");
    this.mPowerWakeLock = localWakeLock1;
    if ((this.mPowerMgr == null) || (this.mPowerWakeLock == null))
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("onCreatemPowerMgr = ");
      PowerManager localPowerManager2 = this.mPowerMgr;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localPowerManager2).append(", ").append("mPowerWakeLock = ");
      PowerManager.WakeLock localWakeLock2 = this.mPowerWakeLock;
      Logger.print(localWakeLock2);
      finish();
      return;
    }
    this.mPowerWakeLock.setReferenceCounted(false);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    boolean bool = super.onCreateOptionsMenu(paramMenu);
    MenuItem localMenuItem1 = paramMenu.add(0, 5, 0, 2130968581).setIcon(2130837513);
    MenuItem localMenuItem2 = paramMenu.add(0, 1, 0, 2130968577).setIcon(2130837512);
    MenuItem localMenuItem3 = paramMenu.add(0, 3, 0, 2130968579).setIcon(2130837514);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = super.onOptionsItemSelected(paramMenuItem);
    int i;
    if (this.mControler == null)
    {
      StringBuilder localStringBuilder = new StringBuilder().append("onOptionsItemSelected()mControler = ");
      FlashlightControler localFlashlightControler = this.mControler;
      Logger.print(localFlashlightControler);
      i = 0;
      return i;
    }
    switch (paramMenuItem.getItemId())
    {
    default:
    case 2:
    case 1:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      i = 1;
      break;
      int j = Log.i("ANALYTIC_Flashlight", "[FlashlightActivity]MENU_AUTOFLASH_OFF mode");
      CloseTwinkle();
      continue;
      int k = Log.i("ANALYTIC_Flashlight", "[FlashlightActivity]MENU_AUTOFLASH mode");
      this.mFLTwinkle.FlashSOS(false);
      this.mFLTwinkle.autoFlashSwitch(true);
      continue;
      int m = Log.i("ANALYTIC_Flashlight", "[FlashlightActivity]MENU_SOS mode");
      this.mFLTwinkle.autoFlashSwitch(false);
      this.mFLTwinkle.FlashSOS(true);
      continue;
      int n = Log.i("ANALYTIC_Flashlight", "[FlashlightActivity]MENU_SOS_OFF mode");
      CloseTwinkle();
      continue;
      int i1 = Log.i("ANALYTIC_Flashlight", "[FlashlightActivity]MENU_MANUAL mode");
      CloseTwinkle();
      Logger.print("MENU_MANUAL sendMessage MSGID_UPDATE_UI");
      Message localMessage1 = this.mHandler.obtainMessage(1);
      localMessage1.arg1 = 1;
      if (!this.mHandler.sendMessage(localMessage1))
        Logger.print("MENU_MANUAL !sendMessage MSGID_UPDATE_UI");
      this.mHandler.removeMessages(2);
      Message localMessage2 = this.mHandler.obtainMessage(2);
      if (!this.mHandler.sendMessageDelayed(localMessage2, 600000L))
        Logger.print("onClick !sendMessageDelayed MSGID_TIME_UP");
      this.mPowerWakeLock.acquire();
    }
  }

  protected void onPause()
  {
    super.onPause();
    if ((this.mControler == null) || (this.mHandler == null) || (this.mPowerWakeLock == null))
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("onPause()mControler = ");
      FlashlightControler localFlashlightControler = this.mControler;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localFlashlightControler).append(", ").append("mHandler = ");
      FLMsgHandler localFLMsgHandler = this.mHandler;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localFLMsgHandler).append(", ").append("mPowerWakeLock = ");
      PowerManager.WakeLock localWakeLock = this.mPowerWakeLock;
      Logger.print(localWakeLock);
      return;
    }
    this.mControler.PowerOff();
    this.mFLTwinkle.autoFlashSwitch(false);
    this.mFLTwinkle.FlashSOS(false);
    this.mHandler.removeMessages(2);
    this.mPowerWakeLock.release();
    BroadcastReceiver localBroadcastReceiver = this.mFLBattery.mBatteryInfoReceiver;
    unregisterReceiver(localBroadcastReceiver);
    Logger.print("releaseFlashRestriction");
    if (this.mFlashRestriction != null)
    {
      this.mFlashRestriction.releaseFlashRestriction();
      this.mFlashRestriction = null;
    }
    if (this.mToast == null)
      return;
    this.mToast.cancel();
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    paramMenu.clear();
    MenuItem localMenuItem1 = paramMenu.add(0, 5, 0, 2130968581).setIcon(2130837513);
    if (this.mFLTwinkle.isAutoFlash())
    {
      MenuItem localMenuItem2 = paramMenu.add(0, 2, 0, 2130968578).setIcon(2130837512);
      if (!this.mFLTwinkle.isSOS())
        break label231;
      MenuItem localMenuItem3 = paramMenu.add(0, 4, 0, 2130968580).setIcon(2130837514);
    }
    while (true)
    {
      MenuItem localMenuItem6;
      if ((this.mFlashRestriction != null) && (this.mFlashRestriction.isDisableFlash() == true))
      {
        MenuItem localMenuItem4 = paramMenu.findItem(3);
        if (localMenuItem4 != null)
          MenuItem localMenuItem5 = localMenuItem4.setEnabled(false);
        localMenuItem6 = paramMenu.findItem(1);
        if (localMenuItem6 != null)
          MenuItem localMenuItem7 = localMenuItem6.setEnabled(false);
      }
      if ((!this.mFLTwinkle.isSOS()) && (!this.mFLTwinkle.isAutoFlash()))
      {
        localMenuItem6 = paramMenu.findItem(5);
        if (localMenuItem6 != null)
          MenuItem localMenuItem8 = localMenuItem6.setEnabled(false);
      }
      return super.onPrepareOptionsMenu(paramMenu);
      MenuItem localMenuItem9 = paramMenu.add(0, 1, 0, 2130968577).setIcon(2130837512);
      break;
      label231: MenuItem localMenuItem10 = paramMenu.add(0, 3, 0, 2130968579).setIcon(2130837514);
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (this.mFlashRestriction == null)
    {
      FLMsgHandler localFLMsgHandler1 = this.mHandler;
      FlashRestriction localFlashRestriction = new FlashRestriction(localFLMsgHandler1);
      this.mFlashRestriction = localFlashRestriction;
      this.mFlashRestriction.initFlashRestriction();
    }
    UpdateCurrentLight();
    UpdateCurrentModeIcon();
    if ((this.mControler == null) || (this.mHandler == null) || (this.mPowerWakeLock == null))
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("onResume()mControler = ");
      FlashlightControler localFlashlightControler = this.mControler;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localFlashlightControler).append(", ").append("mHandler = ");
      FLMsgHandler localFLMsgHandler2 = this.mHandler;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localFLMsgHandler2).append(", ").append("mPowerWakeLock = ");
      PowerManager.WakeLock localWakeLock = this.mPowerWakeLock;
      Logger.print(localWakeLock);
      return;
    }
    if ((this.mFlashRestriction != null) && (this.mFlashRestriction.isDisableFlash() == true));
    while (true)
    {
      BroadcastReceiver localBroadcastReceiver = this.mFLBattery.mBatteryInfoReceiver;
      IntentFilter localIntentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
      Intent localIntent = registerReceiver(localBroadcastReceiver, localIntentFilter);
      int i = Log.d("[ATS][com.htc.flashlight][Launch][successful]", "onResume End");
      return;
      if (this.mControler.getCurrentBrightness() == 0)
        continue;
      this.mControler.PowerOn();
      Message localMessage = this.mHandler.obtainMessage(2);
      if (!this.mHandler.sendMessageDelayed(localMessage, 600000L))
        Logger.print("onResume !sendMessageDelayed MSGID_TIME_UP");
      this.mPowerWakeLock.acquire();
    }
  }

  class FLClickListener
    implements View.OnClickListener
  {
    FLClickListener()
    {
    }

    public void onClick(View paramView)
    {
      int i = FlashlightActivity.this.mControler.getCurrentBrightness();
      int j = FlashlightActivity.this.mControler.GetHigherBrightness();
      if (FlashlightActivity.this.m_bHintOnOff)
        FlashlightActivity.this.closeHint();
      if ((FlashlightActivity.this.mFLTwinkle.isAutoFlash()) || (FlashlightActivity.this.mFLTwinkle.isSOS()))
      {
        j = 0;
        FlashlightActivity.this.CloseTwinkle();
      }
      if ((FlashlightActivity.this.mFlashRestriction != null) && (FlashlightActivity.this.mFlashRestriction.isDisableFlash() == true))
        return;
      if ((FlashlightActivity.this.mControler == null) || (FlashlightActivity.this.mHandler == null) || (FlashlightActivity.this.mPowerWakeLock == null))
      {
        StringBuilder localStringBuilder1 = new StringBuilder().append("FLClickListenermControler = ");
        FlashlightControler localFlashlightControler = FlashlightActivity.this.mControler;
        StringBuilder localStringBuilder2 = localStringBuilder1.append(localFlashlightControler).append(", ").append("mHandler = ");
        FlashlightActivity.FLMsgHandler localFLMsgHandler = FlashlightActivity.this.mHandler;
        StringBuilder localStringBuilder3 = localStringBuilder2.append(localFLMsgHandler).append(", ").append("mPowerWakeLock = ");
        PowerManager.WakeLock localWakeLock = FlashlightActivity.this.mPowerWakeLock;
        Logger.print(localWakeLock);
        return;
      }
      if (j != i)
      {
        Message localMessage1 = FlashlightActivity.this.mHandler.obtainMessage(1);
        localMessage1.arg1 = j;
        if (!FlashlightActivity.this.mHandler.sendMessage(localMessage1))
          Logger.print("onClick !sendMessage MSGID_UPDATE_UI");
      }
      if (i == 0)
        return;
      FlashlightActivity.this.mHandler.removeMessages(2);
      Message localMessage2 = FlashlightActivity.this.mHandler.obtainMessage(2);
      if (!FlashlightActivity.this.mHandler.sendMessageDelayed(localMessage2, 600000L))
        Logger.print("onClick !sendMessageDelayed MSGID_TIME_UP");
      FlashlightActivity.this.mPowerWakeLock.acquire();
      FlashlightActivity.this.mControler.switchPower();
    }
  }

  class FLMsgHandler extends Handler
  {
    FLMsgHandler()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      case 3:
      default:
      case 1:
      case 2:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      }
      while (true)
      {
        super.handleMessage(paramMessage);
        return;
        int i = paramMessage.arg1;
        if (i > 0)
          FlashlightActivity.this.mControler.PowerOn();
        FlashlightActivity.this.mControler.adjustBrightness(i);
        FlashlightActivity.this.UpdateCurrentLight();
        FlashlightActivity.this.UpdateCurrentModeIcon();
        continue;
        FlashlightActivity.this.mControler.adjustBrightness(0);
        FlashlightActivity.this.UpdateCurrentLight();
        FlashlightActivity.this.UpdateCurrentModeIcon();
        FlashlightActivity.this.mControler.PowerOff();
        FlashlightActivity.this.mPowerWakeLock.release();
        continue;
        FlashlightActivity.this.mStatus.setVisibility(0);
        FlashlightActivity.this.mStatus.setImageResource(2130837511);
        continue;
        FlashlightActivity.this.mStatus.setVisibility(8);
        continue;
        int j = FlashlightActivity.this.mControler.getCurrentBrightness();
        if (j == 0)
          j = 3;
        FlashlightActivity.this.mControler.adjustBrightness(j);
        FlashlightActivity.this.UpdateCurrentLight();
        FlashlightActivity.this.SwitchPower();
        FlashlightActivity.this.UpdateCurrentModeIcon();
        continue;
        FlashlightActivity.this.mMain.setImageResource(2130837505);
        FlashlightActivity.this.mControler.PowerOff();
        continue;
        FlashlightActivity.this.mNotice_bg.setVisibility(8);
        FlashlightActivity.this.mNotice_text.setVisibility(8);
        continue;
        FlashlightActivity.this.mMain.setImageResource(2130837505);
        FlashlightActivity.this.mControler.PowerOff();
        if ((FlashlightActivity.this.mFlashRestriction == null) || (FlashlightActivity.this.mFlashRestriction.isDisableFlash() != true))
          continue;
        if (FlashlightActivity.this.mToast == null)
        {
          Logger.print("mToast is null in MSGID_UPDATE_FLASH_FROM_RESTRICTION");
          continue;
        }
        FlashlightActivity.this.mToast.cancel();
        FlashlightActivity.this.mToast.setDuration(1);
        Toast localToast = FlashlightActivity.this.mToast;
        int k = FlashlightActivity.this.mFlashRestriction.getRestrictionHint();
        localToast.setText(k);
        FlashlightActivity.this.mToast.show();
      }
    }
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.FlashlightActivity
 * JD-Core Version:    0.6.0
 */