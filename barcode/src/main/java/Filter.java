
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import zxing.BufferedImageLuminanceSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youzhihao on 2017/9/14.
 */
public class Filter {

    public static void main(String[] args) throws Exception {
        File dir;
        if (args != null&&args.length>0) {
            dir = new File(args[0]);
        } else {
            dir = new File("/Users/youzhihao/Downloads/image/full");
        }
        for (File file : dir.listFiles()) {
            if (!file.getName().endsWith(".jpg")) {
                continue;
            }
            MultiFormatReader formatReader = new MultiFormatReader();
            if (!file.exists()) {
                return;
            }

            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            try {
                Result result = formatReader.decode(binaryBitmap, hints);
                if (!result.toString().contains("/g/")) {
                    file.delete();
                }
            } catch (Exception e) {
                file.delete();
            }
//            System.out.println("二维码格式类型 = " + result.getBarcodeFormat());
//            System.out.println("二维码文本内容 = " + result.getText());
        }
    }

}
