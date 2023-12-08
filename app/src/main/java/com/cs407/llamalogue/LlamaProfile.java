package com.cs407.llamalogue;

public class LlamaProfile {
    int id;
    String name;
    String imgSrc;
    String systemPrompt;

    LlamaProfile(int id, String name, String imgSrc, String systemPrompt) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
        this.systemPrompt = systemPrompt;
    }
}