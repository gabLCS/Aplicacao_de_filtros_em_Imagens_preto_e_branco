package Adjacencia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LetraB {
    public static void main(String[] args) {
        try {
            // Carregar a imagem
            BufferedImage originalImage = ImageIO.read(new File(".\\aviao.png"));
            if (originalImage == null) {
                System.out.println("A imagem não pôde ser carregada.");
                return;
            }
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            // Criar uma nova imagem para armazenar os pixels de fronteira
            BufferedImage borderImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                	borderImage.setRGB(x, y, 0xFFFFFF);
                }
                
            }
            // Definir um limite para identificar os pixels de fronteira
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                	int centerPixel = originalImage.getRGB(x, y) & 0xFF;
                	//Verificando adjacência-4
                    boolean isBorder = TestarAdj4(originalImage,x,y);
                    if (isBorder) {
                        borderImage.setRGB(x, y, centerPixel);
                    }else
                    	//Verificando adjacência-8
                    	if(isBorder = TestarAdj8(originalImage, centerPixel,x,y)) {
                    	borderImage.setRGB(x, y, centerPixel);
                    }
                }
            }
            // Salvar a imagem de fronteira
            File output = new File(".\\aviaoAdjacencia_m.png");
            ImageIO.write(borderImage, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean TestarAdj8(BufferedImage original, int centerPixel,int x, int y) {
    	int threshold = 50; // Ajuste o valor conforme necessário
        int[] surroundingPixels = new int[8];
        surroundingPixels[0] = original.getRGB(x - 1, y - 1) & 0xFF;
        surroundingPixels[1] = original.getRGB(x, y - 1) & 0xFF;
        surroundingPixels[2] = original.getRGB(x + 1, y - 1) & 0xFF;
        surroundingPixels[3] = original.getRGB(x - 1, y) & 0xFF;
        surroundingPixels[4] = original.getRGB(x + 1, y) & 0xFF;
        surroundingPixels[5] = original.getRGB(x - 1, y + 1) & 0xFF;
        surroundingPixels[6] = original.getRGB(x, y + 1) & 0xFF;
        surroundingPixels[7] = original.getRGB(x + 1, y + 1) & 0xFF;
        boolean isBorder = false;

        for (int pixel : surroundingPixels) {
            if (Math.abs(centerPixel - pixel) > threshold) {
                isBorder = true;
                break;
            }
        }
       return isBorder;
    }
    private static boolean TestarAdj4(BufferedImage original,int x, int y) {
    	    int threshold = 50; // Ajuste o valor conforme necessário	
            int centerPixel = original.getRGB(x, y) & 0xFFFFFF; // RGB
            int leftPixel = original.getRGB(x - 1, y) & 0xFFFFFF;
            int rightPixel = original.getRGB(x + 1, y) & 0xFFFFFF;
            int topPixel = original.getRGB(x, y - 1) & 0xFFFFFF;
            int bottomPixel = original.getRGB(x, y + 1) & 0xFFFFFF;

            boolean isBorder = false;

            if (Math.abs(centerPixel - leftPixel) > threshold ||
                Math.abs(centerPixel - rightPixel) > threshold ||
                Math.abs(centerPixel - topPixel) > threshold ||
                Math.abs(centerPixel - bottomPixel) > threshold) {
            	isBorder = true;
            }
            return isBorder;
    }
}