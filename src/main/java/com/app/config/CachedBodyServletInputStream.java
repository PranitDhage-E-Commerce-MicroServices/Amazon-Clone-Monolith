package com.app.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
public class CachedBodyServletInputStream extends ServletInputStream {

    private InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte [] cachedBodyInputStream) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBodyInputStream);
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException e) {
            // TODO Auto-generated stub
            e.printStackTrace();
           return false;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        // TODO Auto-generated stub
    }

    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
}
