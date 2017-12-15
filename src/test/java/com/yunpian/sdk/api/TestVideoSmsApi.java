/**
 * 
 */
package com.yunpian.sdk.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.Template;
import com.yunpian.sdk.model.VideoLayout;
import com.yunpian.sdk.model.VideoLayout.FrameData;
import com.yunpian.sdk.model.VideoLayout.VideoFrame;

/**
 * @author dzh
 * @date Dec 14, 2017 7:26:16 PM
 * @since 1.2.0
 */
public class TestVideoSmsApi extends TestYunpianClient {

    static Logger LOG = LoggerFactory.getLogger(TestVideoSmsApi.class);

    @Test
    @Ignore
    public void getTplTest() {
        Map<String, String> param = clnt.newParam(1);
        param.put(TPL_ID, "1");

        Result<Template> r = clnt.vsms().getTpl(param);
        LOG.info(r.toString());
    }

    @Test
    public void tplBatchSendTest() {
        Map<String, String> param = clnt.newParam(2);
        param.put(TPL_ID, "1");
        param.put(MOBILE, "18616020610,18616020611");

        Result<SmsBatchSend> r = clnt.vsms().tplBatchSend(param);
        LOG.info(r.toString());
    }

    @Test
    @Ignore
    public void addTplTest() throws Exception {
        Map<String, String> param = clnt.newParam(1);
        param.put(SIGN, "【企盆阔记】");

        VideoLayout vl = new VideoLayout();
        vl.setSubject("restapi-" + System.currentTimeMillis());

        VideoFrame frame1 = new VideoFrame().setIndex(1);
        FrameData data1 = new FrameData().setIndex(1).setFileName("data1.txt");
        FrameData data2 = new FrameData().setIndex(2).setFileName("data2.mov");
        frame1.setAttachments(Arrays.asList(data1, data2));

        VideoFrame frame2 = new VideoFrame().setIndex(2);
        FrameData data3 = new FrameData().setIndex(1).setFileName("data3.txt");
        FrameData data4 = new FrameData().setIndex(2).setFileName("data4.mov");
        frame2.setAttachments(Arrays.asList(data3, data4));
        vl.setFrames(Arrays.asList(frame1, frame2));

        InputStream material = new FileInputStream(new File("/Users/dzh/temp/vsms/Archive.zip"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buf = new byte[32];
        while ((len = material.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        material.close();

        Result<Template> r = clnt.vsms().addTpl(param, vl.toString(), baos.toByteArray());
        LOG.info(r.toString());
    }

}
