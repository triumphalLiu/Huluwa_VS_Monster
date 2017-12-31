package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Animation {
    public void fade_out_in(ImageView img, Image nextImg){
        //设置定时器 每100ms降低0.2可见度
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(img.getOpacity() >= 0.025)
                    img.setOpacity(img.getOpacity() - 0.025);
            }
        }, 25, 25);
        //设置定时器 500ms之后自身取消
        Timer fhTimer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                this.cancel();
                //设置新的背景
                img.setImage(nextImg);
                //新建定时器 每100ms增加0.2可见度
                Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(img.getOpacity() <= 0.975)
                            img.setOpacity(img.getOpacity() + 0.025);
                    }
                }, 25, 25);
                //设置定时器 500ms之后自身取消
                Timer fhTimer2 = new Timer();
                fhTimer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        timer2.cancel();
                        this.cancel();
                    }
                }, 1000);
            }
        }, 1000);
    }
}
