public interface Item {
	String getName();
	int getId();
	void leftClick(Vertex v);
	void rightClick(Vertex v);
	default void leftHold(Vertex v, double seconds) {}
	default void rightHold(Vertex v, double seconds) {}
	Image getImage();
}