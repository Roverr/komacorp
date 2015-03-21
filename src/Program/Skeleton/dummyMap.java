package Program.Skeleton;

import java.util.List;

import Program.Graphics.Line;

public class dummyMap {
	private List<Line> CheckPoints;
	private List<dummyMapItem> MapItems;
	private List<dummyRobot> Robots;
	private List<Line> Track;

	public void AddMapItem(dummyMapItem item) {

	}

	public List<String> GetResult() {
		return null;

	}

	public List<dummyRobot> GetRobots() {
		return Robots;
	}

	public void LoadMap(String file) {

	}

	public void RemoveMapItem(dummyMapItem item) {
		if (MapItems.contains(item)) {
			MapItems.remove(item);
		}
	}
	
	public void ValidateState(dummyRobot robot){
		
	}
}
