package com.yunpian.sdk.model;

import java.util.List;

import com.yunpian.sdk.util.JsonUtil;

/**
 * 云片的视频短信模版布局，支持转到其他供应商
 * 
 * 非业务字段vl开头
 * 
 * <pre>
 * 资源包结构,平行结构,不支持目录
 * - zip根目录
 *  - file1.txt
 *  - file2.jpeg
 *  - file3.mpeg
 * </pre>
 * 
 */
public class VideoLayout {

    private String vlVersion = "0.0.1"; // layout的版本号

    private String subject;

    private List<VideoFrame> frames;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVlVersion() {
        return vlVersion;
    }

    public void setVlVersion(String vlVersion) {
        this.vlVersion = vlVersion;
    }

    public List<VideoFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<VideoFrame> frames) {
        this.frames = frames;
    }

    public static class VideoFrame {
        private int index = 1;
        private int playTimes = 1; // 播放次数
        private List<FrameData> attachments;

        public int getIndex() {
            return index;
        }

        public VideoFrame setIndex(int index) {
            this.index = index;
            return this;
        }

        public int getPlayTimes() {
            return playTimes;
        }

        public VideoFrame setPlayTimes(int playTimes) {
            this.playTimes = playTimes;
            return this;
        }

        public List<FrameData> getAttachments() {
            return attachments;
        }

        public VideoFrame setAttachments(List<FrameData> attachments) {
            this.attachments = attachments;
            return this;
        }
    }

    public static class FrameData {
        private int index = 1;
        private String fileName; // zip里的文件名，如file1.txt。模板创建后改为 文件id.后缀 根据这个字段获取文件

        public int getIndex() {
            return index;
        }

        public FrameData setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getFileName() {
            return fileName;
        }

        public FrameData setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

    }

    public String toString() {
        return JsonUtil.toJson(this);
    }

}
