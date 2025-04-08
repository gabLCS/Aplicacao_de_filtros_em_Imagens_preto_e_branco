package HistEqualization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Histograma {
    public static void main(String[] args) {
    	// Selecionando os arquivos de entrada e os de saída
    	String arquivo = ".\\einstein.jpg";
//    	String arquivoMod = ".\\EinsteinEqualizado.jpg";
//    	String arquivo = ".\\lena_gray.bmp";
//    	String arquivoMod = ".\\lena_grayEqualizado.bmp";
//    	String arquivo = ".\\lena_grayEqualizado.bmp";
//    	String arquivoMod = ".\\lena_grayULTRAEqualizado.bmp";
//    	String arquivo = ".\\EinsteinEqualizado.jpg";
//    	String arquivoMod = ".\\EinsteinULTRAEqualizado.jpg";
    	String arquivoMod = ".\\PolemMod.png";
    	int Histograma[] = new int[256];
    	double HistogramaNorm [] = new double[256];
    	double HistogramaAc [] = new double[256];
    	int tamanho = 0;
        try {
            // Carregar a imagem
            BufferedImage originalImage = ImageIO.read(new File(arquivo));

            if (originalImage == null) {
                System.out.println("A imagem não pôde ser carregada.");
                return;
            }
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            //passando pela imagem e montando o histograma
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                	int centerPixel = originalImage.getRGB(x, y) & 0xFF;
                	Histograma[centerPixel]++;
                	tamanho++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Montando o Histograma normalizado com base no histograma anterior
        int j=0;
        for(int y: Histograma) {
        	HistogramaNorm[j] = (double)y/tamanho;
        	j++;
        }
        //Montando o Histograma acumulado com base no histograma normalizado
        j=0;
        double total = 0;
        for(double z: HistogramaNorm) {
        	total = total +z;
        	HistogramaAc[j] = total;
        	j++;
        }
        //Aplicando a fórmula para encontrar o histograma equalizado
        int HistogEqu[] = new int [256];
        j=0;
        double var;
        for(double a : HistogramaAc) {
        	var = a*(double)255;
        	HistogEqu[j] = (int) Math.round(var);
        	j++;
        }
        
        //imprimir os histogramas
        for(j=0; j<256; j++) {
//        	System.out.println(Histograma[j]);
//        	System.out.println(HistogramaNorm[j]);
//        	System.out.println(HistogramaAc[j]);
//        	System.out.println(HistogEqu[j]);
        	
        }
        
        
        //Usando o Histograma equalizado para equalizar uma imagem
        
        try {
            // Carregar a imagem original
            BufferedImage originalImage = ImageIO.read(new File(arquivo));
            if (originalImage == null) {
                System.out.println("A imagem original não pôde ser carregada.");
                return;
            }

            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            // Criar uma nova imagem com as mesmas dimensões
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

            // Copiar os pixels da imagem original para a nova imagem aplicando a função de equalização do livro
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = originalImage.getRGB(x, y) & 0xFF;
                    pixel = calculateEqualizedValue(HistogramaAc, pixel); // fazendo equalização do histograma.
                    int newGrayPixel = (pixel << 16) | (pixel << 8) | pixel;
                    newImage.setRGB(x, y, newGrayPixel);
                }
            }

            // Salvar a nova imagem
            File output = new File(arquivoMod);
            ImageIO.write(newImage, "jpg", output);

            System.out.println("Nova imagem criada com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   private static int calculateEqualizedValue(double[] histograma, int pixel) {
	   double var = histograma[pixel]*(double)255;
	   return (int) Math.round(var);
   }

}
