import java.util.ArrayList;
public class World {
    ArrayList<Triangle> triangles = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    public World() {
        for (int i = -49.5; i <= 49.5; i++) {
            for (int j = -49.5; i <= 49.5; i++) {
                triangles.add(new Triangle(
                    new Vertex(i - 0.5, 0, j - 0.5),
                    new Vertex()
                ));
            }
        }
    }
}