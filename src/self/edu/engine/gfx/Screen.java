package self.edu.engine.gfx;

public class Screen {

	private int width, height;
	public int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}

	public void clear() {
		clear(0xff000000);
	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Assumes that the parameter is the same dimension as the screen pixels
	 * 
	 * @param pixels
	 */
	public void display(int[] pixels) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = this.pixels[i];
	}

	public void render(Sprite sprite, int x, int y) {
		for (int i = 0; i < sprite.getHeight(); i++) {
			int yy = y + i;
			if (yy < 0)
				continue;
			else if (yy >= height)
				break;

			for (int j = 0; j < sprite.getWidth(); j++) {
				int xx = x + j;
				if (xx < 0)
					continue;
				else if (xx >= width)
					break;

				buffer(sprite.getPixel(j, i), xx, yy);
			}
		}
	}

	public void render(Sprite sprite, int x, int y, int scale) {
		for (int i = 0; i < sprite.getHeight(); i++) {
			for (int a = 0; a < scale; a++) {
				int yy = y + i * scale + a;
				if (yy < 0)
					continue;
				else if (yy >= height)
					break;

				for (int j = 0; j < sprite.getWidth(); j++) {
					for (int b = 0; b < scale; b++) {
						int xx = x + j * scale + b;
						if (xx < 0)
							continue;
						else if (xx >= width)
							break;

						buffer(sprite.getPixel(j, i), xx, yy);
					}
				}
			}
		}
	}

	/**
	 * renders the specified sprite at x,y as its top left corner rotated by the
	 * specified angle measured in radians;
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param angle
	 */
	public void render(Sprite sprite, int x, int y, double angle) {
		double cos = Math.cos(angle);
		double sin = -Math.sin(angle);

		y += sprite.getHeight() / 2;
		x += sprite.getWidth() / 2;

		for (int i = 0; i < sprite.getHeight(); i++) {
			int yy = i - sprite.getHeight() / 2;
			for (int j = 0; j < sprite.getWidth(); j++) {
				int xx = j - sprite.getWidth() / 2;

				buffer(sprite.getPixel(j, i), (int) (x + xx * cos + yy * -sin),
						(int) (y + xx * sin + yy * cos));
				// buffer(sprite.getPixel(j, i), xx
				// - (int) (xxx * cos + yyy * -sin), yy
				// + (int) (xxx * sin + yyy * cos));
			}
		}

	}

	/**
	 * Renders the specified text at x,y as its top left corner as the specified
	 * color
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param scale
	 * @param color
	 */
	public void renderText(Sprite sprite, int x, int y, int scale, int color) {
		color &= 0xffffffff;

		for (int i = 0; i < sprite.getHeight(); i++) {
			for (int a = 0; a < scale; a++) {
				int yy = y + i * scale + a;
				if (yy < 0)
					continue;
				else if (yy >= height)
					break;

				for (int j = 0; j < sprite.getWidth(); j++) {
					for (int b = 0; b < scale; b++) {
						int xx = x + j * scale + b;
						if (xx < 0)
							continue;
						else if (xx >= width)
							break;

						buffer(sprite.getPixel(j, i), color, xx, yy);
					}
				}
			}
		}
	}

	/**
	 * Buffers the specified col2 to the specified coordinate
	 * 
	 * @param col
	 * @param x
	 * @param y
	 */
	public void buffer(int col1, int col2, int x, int y) {
		if (col1 == 0xffff00ff || col1 == 0xff7f007f)
			// two colors used to represent transparent colors, if it is one of
			// these colors, ignore them
			return;

		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		pixels[y * width + x] = col2;
	}

	/**
	 * Buffers the specified color to the specified coordinate
	 * 
	 * @param col
	 * @param x
	 * @param y
	 */
	public void buffer(int col, int x, int y) {
		if (col == 0xffff00ff || col == 0xff7f007f)
			// two colors used to represent transparent colors, if it is one of
			// these colors, ignore them
			return;

		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		pixels[y * width + x] = col;
	}

}
