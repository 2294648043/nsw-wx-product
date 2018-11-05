package com.nsw.wx.product.util;

import com.nsw.wx.product.pojo.TbWeChatProduct;
import com.nsw.wx.product.pojo.updateProduct;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;

public class CopyUtils {
    /**
     * 一个类把属性值赋值给另一个类的相同的属性
     * @param source 当前类
     * @param dest 新的类
     * @throws Exception
     */
    public static void Copy(Object source, Object dest) throws Exception {
        // 获取属性
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < sourceProperty.length; i++) {

                for (int j = 0; j < destProperty.length; j++) {

                    if (sourceProperty[i].getName().equals(destProperty[j].getName())  && sourceProperty[i].getPropertyType() == destProperty[j].getPropertyType()) {
                        // 调用source的getter方法和dest的setter方法
                        destProperty[j].getWriteMethod().invoke(dest,sourceProperty[i].getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TbWeChatProduct d = new TbWeChatProduct();
        updateProduct dd = new updateProduct();
        dd.setActivitytype(0);
        dd.setOtherprice("845");
        try {
            new CopyUtils().Copy(dd,d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------->"+d.getActivitytype());
        System.out.println("-------------->"+d.getOtherprice());
    }
}
