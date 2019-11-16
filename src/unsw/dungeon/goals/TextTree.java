package unsw.dungeon.goals;

import java.util.ArrayList;

public class TextTree {
	public static String createTextTree(Goal g) {
		return createTextTree(g, 0);
	}

	private static String createTextTree(Goal g, int n_indent) {
		String indent = "";
		for (int i = 0; i < n_indent; i++) {
			indent += "  ";
		}

		String result = "";

		if (g instanceof GoalComposite) {
			GoalComposite gs = (GoalComposite) g;

			String name = null;
			String prefix = null;

			if (gs.strategy instanceof GoalStrategyOR) {
				name = "OR";
				prefix = "\\";
			} else if (gs.strategy instanceof GoalStrategyAND) {
				name = "AND";
				prefix = "+";
			}

			result += name + "\n";

			ArrayList<Goal> remainingGoals = gs.getRemainingGoals();
			if (remainingGoals.size() == 1) {
				return createTextTree(remainingGoals.get(0), n_indent);
			} else {
				for (Goal s : remainingGoals) {
					result += indent + prefix + " " + createTextTree(s, n_indent + 1);
				}
			}
			return result;

		} else {
			return g.toString() + "\n";
		}
	}

}
