package com.shuiwangzhijia.shuidian.event;

import java.io.File;

/**
 * created by wangsuli on 2018/8/31.
 */
public class UpdateEvent {
    public final int state;
    public final int progress;
    public File file;

    public UpdateEvent(int state, int progress, File file) {
        this.state=state;
        this.progress=progress;
        this.file=file;
    }
}
