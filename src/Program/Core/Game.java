package Program.Core;

import Program.Helpers.Vector;
import Program.Skeleton.SkeletonUtility;

public class Game {
		private Map GameMap;
		

		public void StartGame(){
			SkeletonUtility.printCall("StartGame", this);
			for (Robot r : GameMap.GetRobots()) {
				r.ModifySpeed(new Vector(0,0));
			}
			SkeletonUtility.printReturn("StartGame", this);
		}
		
		
		public void EndGame(){
			SkeletonUtility.printCall("EndGame", this);
			GameMap.GetResult();
			SkeletonUtility.printReturn("EndGame", this);
		}
		

		public void Run(){
			SkeletonUtility.printCall("Run", this);
			for (Robot r : GameMap.GetRobots()) {
				GameMap.ValidateState(r);
				r.Jump();
			}
			SkeletonUtility.printReturn("Run", this);
		}
		
		public void UserControl(char interact){
			SkeletonUtility.printCall("UserControl", this);
			//...//
			SkeletonUtility.printReturn("UserControl", this);
		}
		
}
