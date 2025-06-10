/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.voi.voidesktopui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** VOI brand primitives – exactly the same hex values as the HTML variables */
public final class Theme {
    private Theme() {}
    public static final Color BLUE   = Color.web("#003776");
    public static final Color SKY    = Color.web("#E7F2FF");
    public static final Color SNOW   = Color.web("#F9FBFC");
    public static final Color TEXT   = Color.web("#3E4C59");
    public static final Color ACCENT = Color.web("#F5A623");

    public static Font kpiFont() { return Font.font("Segoe UI", FontWeight.SEMI_BOLD, 32); }
    public static Font h2()      { return Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18); }
    public static Font base()    { return Font.font("Segoe UI", 14); }
}
