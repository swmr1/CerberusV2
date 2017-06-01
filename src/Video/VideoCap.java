package Video;
import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Range;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class VideoCap {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();

    
    VideoCap(int cam){
        cap = new VideoCapture();
        cap.open(cam);
        
    } 
 
    Mat getOneFrame() {
        cap.read(mat2Img.mat);
        BufferedImage img = mat2Img.getImage(mat2Img.mat);
        Mat mat = new Mat(img.getHeight(), img.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, Mat2Image.dat);

        Mat mat1 = new Mat(img.getHeight(),img.getWidth(),CvType.CV_8UC1);
        Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

        byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
        mat.put(0, 0, Mat2Image.dat);
       
        mat1.get(0, 0, data1);
        BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
        image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);    
        return mat1;
    }
}