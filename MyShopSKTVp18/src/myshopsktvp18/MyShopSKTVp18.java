/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myshopsktvp18;

import classes.App;

/**
 *
 * @author Sveta
 */
public class MyShopSKTVp18 {

    public static void main(String[] args) {
     
        String flag = "base";
        if(args != null && args.length != 0){
            flag = args[0];
        }
        App app = new App(flag);
        app.run();
    }
    
}
