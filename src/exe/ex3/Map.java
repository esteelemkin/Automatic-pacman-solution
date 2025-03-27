package exe.ex3;

import java.util.*;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
	int[][] _map;
	private boolean _cyclicFlag = true;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 *
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
		init(w, h, v);
	}

	/**
	 * Constructs a square map (size*size).
	 *
	 * @param size
	 */
	public Map(int size) {
		this(size, size, 0);
	}

	/**
	 * Constructs a map from a given 2D array.
	 *
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	/**
	 *
	 * @param w the width of the underlying 2D array.
	 * @param h the height of the underlying 2D array.
	 * @param v the init value of all the entries in the 2D array.
	 */
	@Override
	public void init(int w, int h, int v) {
		if (w <= 0 || h <= 0) { //w & h must be positive
			throw new IllegalArgumentException("w and h must be positive");
			}
		this._map = new int[h][w];

		for (int i = 0; i < h; i++) {            //// Thse for loops go through every position in the 2D array.
			for (int j = 0; j < w; j++) {
					this._map[i][j] = v;             // For each position, it sets the value to v.
				}
			}
		}


	@Override
	/**
	 * This method initializes the map using a 2D array.
	 */
	public void init(int[][] arr) {
		this._map = new int[arr.length][arr[0].length];	// creates a new 2D array with the same dimensions as the input array 'arr'.
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {	// for loops iterate through every element of the input array.
				this._map[i][j] = arr[i][j];        // For each element of the input array, the corresponding position in the map is set to the same value.
			}
		}
	}

	/**
	 *
	 * @return deep copy of the map to avoid having a mutation  the original map
	 */

	@Override
	public int[][] getMap() {
		if (this._map == null) return null;
		int[][] ans = new int[_map.length][];
		for (int i = 0; i < _map.length; i++) {      // This for loop iterates through every row in the map.
			ans[i] = _map[i].clone();                     // For each row, it creates a new row in 'ans' that is a clone of the row in the map.
			                                            //  clone() is used here to create a deep copy of each row.
			}
		return ans;
	}


	/**
	 * This method is used to return the width of the map.
	 *
	 * The width of the map is determined by the number of columns in the map,
	 * which is represented by the length of any row (we assume that all rows have the same length).
	 */
	@Override
	public int getWidth() {
		if (_map != null && _map.length > 0) {      // Check if the map exists and has at least one row.
			return _map[0].length;                 // If the map has at least one row, return the length of the first row as the width.
		} else {
			return 0;
		}
	}


	/**
	 * This method is used to return the height of the map.
	 *
	 * The height of the map is determined by the number of rows in the map,
	 * which is represented by the length of the outer array.
	 */
	@Override
	public int getHeight() {
		if (_map != null && _map.length > 0) {
			return _map.length;        // If the map has at least one row, return the length of the outer array as the height.
		} else {
			return 0;
		}
	}


	/**
	 * This method is used to get the value of the pixel at the specific coordinate (x, y).
	 *
	 * If the map is cyclic (meaning the map wraps around from end to start), it uses modulus with the
	 * height and width to get the correct coordinate. This allows for continuous movement, where moving
	 * off one edge of the map will result in appearing on the opposite edge.
	 *
	 * If the map is not cyclic, it accesses the coordinate in the map directly.
	 *
	 * If the specified coordinate is out of the map boundaries, it returns -1 as an error value.
	 */
	@Override
	public int getPixel(int x, int y) {
		if(isCyclic())                                        // If the map is cyclic, calculate the actual coordinates by using modulus.
			return _map[x % getHeight()][y % getWidth()];
		if (x <= _map.length && y <= _map[0].length)     // If the map is not cyclic and the coordinate is within the map boundaries, return the pixel value.
			return _map[x][y];
		return -1;
	}

	/**
	 * This method is a convenience method that allows retrieving a pixel's value by its Pixel2D object.
	 */
	@Override
	public int getPixel(Pixel2D p) {
		return this.getPixel(p.getX(),p.getY());
	}

	/**
	 * This method sets the value of a pixel at a specified (x, y) location.
	 * If the map is set as cyclic (indicated by the _cyclicFlag), it uses modulo operation to calculate the actual
	 * positions, the map wraps around at the edges.
	 * If the map is not cyclic, it first checks if the coordinates are within the map bounds before setting the value.
	 * If the coordinates are out of map bounds, it prints an error message.
	 *
	 * @param x The x-coordinate of the pixel.
	 * @param y The y-coordinate of the pixel.
	 * @param v The value to be set for the pixel.
	 */
	@Override
	public void setPixel(int x, int y, int v) {
		if(isCyclic())
			_map[x % getHeight()][y % getWidth()]=v;
		else if (x <= _map.length && y <= _map[0].length)
			_map[x][y]=v;
		else
			System.out.println("location out of bounds");
	}

	/**
	 * This method sets the value of a pixel at a location specified by a Pixel2D object.
	 * This is a convenience method that calls the `setPixel(int x, int y, int v)` method.
	 * It extracts the x and y coordinates from the Pixel2D object and then passes them along with the pixel value to the other setPixel method.
	 *
	 * @param p The Pixel2D object that contains the x and y coordinates of the pixel.
	 * @param v The value to be set for the pixel.
	 */
	@Override
	public void setPixel(Pixel2D p, int v) {
		setPixel(p.getX(),p.getY(), v);
	}


	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 * @param xy The Pixel2D object indicating the starting point for the fill operation.
	 *  * @param new_v The new color to be used for the fill operation.
	 *  * @return 0 after the operation is complete. (This could be updated to return a more meaningful value, such as the count of pixels changed, if necessary.)
	 *  */

	public int fill(Pixel2D xy, int new_v) {
		int initialColor = getPixel(xy);          // Gets the initial color of the pixel at the location xy
			if(initialColor==new_v)                   // If the initial color is the same as the new color, then no need to fill, return 0
				return 0;

			// Creates Index2D objects for each of the 4 directions from xy: right, down, up, and left
			// If the map is cyclic, it handles wrapping from the end of the map to the start

			Index2D RightPixel=new Index2D((xy.getX()+1) % getHeight(),xy.getY());
			Index2D DownPixel=new Index2D(xy.getX(),(xy.getY()+1)% getWidth());
			Index2D UpPixel=new Index2D(xy.getX(),(xy.getY()-1)% getWidth());
			Index2D LeftPixel=new Index2D((xy.getX()-1) % getHeight(),xy.getY());

			// Checks each direction, if the pixel in that direction is of the same color as the initial color, then recursively fills from there
			setPixel(xy ,new_v);
			if(initialColor==getPixel(RightPixel))
				fill(RightPixel, new_v); //right
			if(initialColor==getPixel(UpPixel))
				fill(UpPixel, new_v); //up
			if (initialColor==getPixel(DownPixel))
				fill(DownPixel, new_v); //down
			if (initialColor==getPixel(LeftPixel))
				fill(LeftPixel, new_v); //left

			return 0;
		}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 * This method computes the shortest path between two points in the map using a Breadth-first search (BFS) algorithm.
	 * @param p1 The starting pixel.
	 * @param p2 The destination pixel.
	 * @param obsColor The color representing an obstacle, which cannot be traversed.
	 * @return An array of Pixel2D objects representing the shortest path from p1 to p2. If no path is found, it returns null.
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
		Queue<Pixel2D> queue = new LinkedList<>();
		HashMap<Pixel2D, Pixel2D> previousPixelMap = new HashMap<>();
		boolean[][] visited = new boolean[_map.length][_map[0].length];  // A 2D boolean array to keep track of visited pixels
		queue.add(p1);

		while (!queue.isEmpty()) {
			Pixel2D currentNode = queue.remove();
			if (currentNode.getX() == p2.getX() && currentNode.getY() == p2.getY()) {         // If it's the destination pixel, we found the path
				List<Pixel2D> path = new ArrayList<>();                                 // Construct the path from the destination to the start by following the predecessors
				while (currentNode != null) {
					path.add(currentNode);
					currentNode = previousPixelMap.get(currentNode);
				}
				Collections.reverse(path);                                          // Reverse the path to get it from the start to the destination
				return ListToArray(path);
			} else {
				for (Pixel2D neighbor : this.getNeighbors(currentNode)) {              // Visit all unvisited neighbors of the current pixel that are not obstacles
					if (neighbor.getX() == -1 || neighbor.getY() == -1) {
						System.out.println("not cool");
					}
					if (getPixel(neighbor) != obsColor && !visited[neighbor.getX()][neighbor.getY()]) {
						queue.add(neighbor);
						visited[neighbor.getX()][neighbor.getY()] = true;
						previousPixelMap.put(neighbor, currentNode);

					}
				}
			}
		}
		return null;
	}


	/**
	 * /**
	 *  * This method converts a list of Pixel2D objects to an array of Pixel2D objects.
	 *  *
	 *  * @param list The list of Pixel2D objects to be converted.
	 *  * @return The resulting array of Pixel2D objects.
	 *  */
	private Pixel2D[] ListToArray(List<Pixel2D> list) {
		Pixel2D[] parray = new Pixel2D[list.size()];
		for (int i=0; i < parray.length; i++) {
			parray[i] = list.get(i);
		}
		return parray;
	}


	/**
	 * This method retrieves the neighboring pixels of a given pixel.
	 * It differentiates between cyclic and non-cyclic maps.
	 *
	 * @param currentP The pixel for which to find neighbors.
	 * @return A list of neighboring Pixel2D objects.
	 */
	private List<Pixel2D> getNeighbors(Pixel2D currentP) {
		if (this._cyclicFlag)
			return getNeighborsCyclic(currentP);
		return getNeighborsNonCyclic(currentP);
	}

	/**
	 * This method retrieves the neighboring pixels of a given pixel for non-cyclic maps.
	 * Neighboring pixels are calculated as right, left, up, and down from the current pixel.
	 *
	 * @param currentP The pixel for which to find neighbors.
	 * @return A list of neighboring Pixel2D objects.
	 */
	private List<Pixel2D> getNeighborsNonCyclic(Pixel2D currentP) {
		List<Pixel2D> neighbors = new ArrayList<>();
		Pixel2D right = new Index2D(currentP.getX() + 1, currentP.getY());
		Pixel2D left = new Index2D(currentP.getX() - 1, currentP.getY());
		Pixel2D down = new Index2D(currentP.getX(), currentP.getY() + 1);
		Pixel2D up = new Index2D(currentP.getX(), currentP.getY() - 1);

		// If the calculated neighboring pixel is inside the map, add it to the list of neighbors

		if (this.isInside(right))
			neighbors.add(right);
		if (this.isInside(left))
			neighbors.add(left);
		if (this.isInside(up))
			neighbors.add(up);
		if (this.isInside(down))
			neighbors.add(down);
		return neighbors;
	}

	/**
	 * This method retrieves the neighboring pixels of a given pixel for cyclic maps.
	 * Neighboring pixels are calculated as right, left, up, and down from the current pixel.
	 * In cyclic maps, the map wraps around such that the right neighbor of the rightmost pixel
	 * is the leftmost pixel, and similar for other directions.
	 *
	 * @param currentP The pixel for which to find neighbors.
	 * @return A list of neighboring Pixel2D objects.
	 */
	private List<Pixel2D> getNeighborsCyclic(Pixel2D currentP) {
		List<Pixel2D> neighbors = new ArrayList<>();

		// Calculate the location of the neighboring pixels, considering the map wraps around
		Pixel2D right = new Index2D((currentP.getX() + 1 + this.getHeight()) % this.getHeight(), (currentP.getY() + this.getWidth()) % this.getWidth());
		Pixel2D left = new Index2D((currentP.getX() - 1 + this.getHeight()) % this.getHeight(), (currentP.getY() + this.getWidth()) % this.getWidth());
		Pixel2D down = new Index2D((currentP.getX() + this.getHeight()) % this.getHeight(), (currentP.getY() + 1 + this.getWidth()) % this.getWidth());
		Pixel2D up = new Index2D((currentP.getX() + this.getHeight()) % this.getHeight(), (currentP.getY() - 1 + this.getWidth()) % this.getWidth());

		// If the calculated neighboring pixel is inside the map, add it to the list of neighbors
		if (this.isInside(right))
			neighbors.add(right);
		if (this.isInside(left))
			neighbors.add(left);
		if (this.isInside(up))
			neighbors.add(up);
		if (this.isInside(down))
			neighbors.add(down);
		return neighbors;
	}

	/**
	 * This method checks if a given pixel is inside the map's boundaries.
	 * It checks both the x and y coordinates of the pixel against the map's dimensions.
	 *
	 * @param p The pixel to check.
	 * @return A boolean indicating whether the pixel is inside the map's boundaries.
	 */
	@Override
	public boolean isInside(Pixel2D p) {
		if (p.getX() < _map.length && p.getX() >= 0 && p.getY() < _map[0].length && p.getY() >= 0) // If both the x and y coordinates of the pixel are less than or equal to the map's dimensions, return true
			return true;
		else
			return false;
	}


	/**
	 * checks if the map is cyclic or not.
	 * @return A boolean indicating whether the map has the cyclic property or not.
	 */
	@Override
	public boolean isCyclic() {
		return _cyclicFlag;
	}

	/**
	 * This method allows for setting the cyclic property of the map.
	 * @param cy A boolean value indicating whether the map should be set to cyclic (true) or not (false).
	*/
	@Override
	public void setCyclic(boolean cy) {
		_cyclicFlag=cy;
	}

	/**
	 * This method calculates and returns a new map where the value of each pixel represents the shortest distance
	 * from the start pixel to that pixel. Pixels that contain an obstacle (obsColor) or are unreachable from the start
	 * pixel are marked with -1.
	 *
	 * @param start The start pixel for the calculation of distances.
	 * @param obsColor The color of obstacle pixels. Distances are not calculated for these pixels, and they are marked with -1.
	 * @return A new map where each pixel's value represents the shortest distance from the start pixel to that pixel.
	 */
	@Override
	public Map2D allDistance(Pixel2D start, int obsColor) {
		Map2D ans = new Map(getHeight(),getWidth(),0);  // the result.
		Pixel2D[] path;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				path = shortestPath(new Index2D(0, 0), new Index2D(i, j), obsColor);	   // Calculate the shortest path from the start pixel to the current pixel.
				if (path == null || getPixel(i, j) == obsColor) {                              // If no path was found or the pixel is an obstacle, set its value to -1.
					ans.setPixel(i, j, -1);
				} else {
					ans.setPixel(i, j, path.length - 1);
				}
			}
		}
		return ans;
	}
}






