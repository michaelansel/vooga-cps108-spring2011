package vooga.arcade.helper;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor
{
	public static BufferedImage loadImage(String imageFullPath)
	{
		try
		{
			return ImageIO.read(new File(imageFullPath));
		}
		catch (IOException e)
		{
		}
		return null;
	}

	public static BufferedImage scaleImage(BufferedImage b, double xScale,
			double yScale)
	{
		BufferedImage scaledImage = new BufferedImage(
				(int) (b.getWidth() * xScale), (int) (b.getHeight() * yScale),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		AffineTransform xform = AffineTransform
				.getScaleInstance(xScale, yScale);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(b, xform, null);
		graphics2D.dispose();
		return scaledImage;
	}

	public static BufferedImage scaleImageWithSetX(BufferedImage b, int x)
	{
		double yScale = (double) x / (double) b.getWidth();
		return scaleImage(b, yScale, yScale);
	}

	public static BufferedImage scaleImageWithSetY(BufferedImage b, int y)
	{
		double xScale = (double) y / (double) b.getHeight();
		return scaleImage(b, xScale, xScale);
	}

	public static BufferedImage padImageToSquare(BufferedImage b, int width)
	{
		AffineTransform xform = new AffineTransform();

		if (b.getWidth() > width || b.getHeight() > width)
		{
			if (b.getWidth() > b.getHeight())
			{
				xform.scale((double) width / (double) b.getWidth(),
						(double) width / (double) b.getWidth());
			}
			else
			{
				xform.scale((double) width / (double) b.getHeight(),
						(double) width / (double) b.getHeight());
			}
		}
		
		if(b.getWidth() != b.getHeight())
		{
			xform.translate((b.getWidth() - width),
					 0);
		}
		BufferedImage scaledImage = new BufferedImage(width, width,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = scaledImage.createGraphics();

		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(b, xform, null);
		graphics2D.dispose();
		return scaledImage;

	}
}
