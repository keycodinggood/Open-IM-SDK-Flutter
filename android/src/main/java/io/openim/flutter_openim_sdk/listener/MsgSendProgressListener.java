package io.openim.flutter_openim_sdk.listener;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.openim.flutter_openim_sdk.util.CommonUtil;
import open_im_sdk.SendMsgCallBack;

public class MsgSendProgressListener implements SendMsgCallBack {
    final private MethodChannel.Result result;
    final private MethodCall call;
    final Map<String, Object> values = new HashMap<>();

    public MsgSendProgressListener(MethodChannel.Result result, MethodCall call) {
        this.result = result;
        this.call = call;
    }

    @Override
    public void onError(long l, String s) {
        CommonUtil.runMainThreadReturnError(result, l, s, null);
    }

    @Override
    public void onProgress(long l) {
        values.put("clientMsgID", CommonUtil.getSendMessageClientMsgID(call));
        values.put("progress", l);
        CommonUtil.emitEvent("msgSendProgressListener", "onProgress", values);
    }

    @Override
    public void onSuccess(String s) {
        CommonUtil.runMainThreadReturn(result, s);
    }
}
