/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeroboo.abbyydump;

/**
 *
 * @author pthung
 */
public class TextItem {

    String id;
    String page;
    String block;
    int line;
    String content;
    int x;
    int y;
    int width;
    int height;

    public TextItem(String id, String page, String block, int line, String content, int x, int y, int width, int height) {
        this.id = id;
        this.page = page;
        this.block = block;
        this.line = line;
        this.content = content;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public String getBlock() {
        return block;
    }

    public int getLine() {
        return line;
    }

    public String getContent() {
        return content;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPage() {
        return page;
    }

}
