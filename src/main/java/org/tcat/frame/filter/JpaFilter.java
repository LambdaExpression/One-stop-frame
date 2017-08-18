//package org.tcat.frame.filter;
//
//import org.hibernate.event.internal.DefaultLoadEventListener;
//import org.hibernate.event.spi.PostLoadEvent;
//import org.hibernate.event.spi.PostLoadEventListener;
//import org.springframework.core.annotation.Order;
//
///**
// * Created by Lin on 2017/8/17.
// */
//@Order(1)
//public class JpaFilter extends DefaultLoadEventListener implements PostLoadEventListener {
//
//    @Override
//    public void onPostLoad(PostLoadEvent event) {
//        System.out.println(event.getEntity().getClass().getName() + ":更新完毕");
//    }
//
//}
