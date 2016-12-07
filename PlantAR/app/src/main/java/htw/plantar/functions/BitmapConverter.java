package htw.plantar.functions;

import android.graphics.Bitmap;

public class BitmapConverter 
{
	public static byte[] ConvertToNV21(Bitmap bitmap)
	{
		int inputWidth = bitmap.getWidth();
		int inputHeight = bitmap.getHeight();
		
		int[] argb = new int[inputWidth * inputHeight];
		bitmap.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
		byte[] yuv = new byte[inputWidth * inputHeight * 3/2];
		EncodeYUV420SP(yuv, argb, inputWidth, inputHeight);
		bitmap.recycle();
		return yuv;
	}
	
	private static void EncodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) 
	{
        final int frameSize = width * height;

        int yIndex = 0;
        int uvIndex = frameSize;
        int R, G, B, Y, U, V;
        int index = 0;
        
        for (int j = 0; j < height; j++) 
        {
            for (int i = 0; i < width; i++) 
            {
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff);

                Y = ( (  66 * R + 129 * G +  25 * B + 128) >> 8) +  16;
                U = ( ( -38 * R -  74 * G + 112 * B + 128) >> 8) + 128;
                V = ( ( 112 * R -  94 * G -  18 * B + 128) >> 8) + 128;
                yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                
                if (j % 2 == 0 && index % 2 == 0)
                {
                    yuv420sp[uvIndex++] = (byte)((V<0) ? 0 : ((V > 255) ? 255 : V));
                    yuv420sp[uvIndex++] = (byte)((U<0) ? 0 : ((U > 255) ? 255 : U));
                }

                index ++;
            }
        }
    }
}
