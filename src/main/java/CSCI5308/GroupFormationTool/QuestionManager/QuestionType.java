package CSCI5308.GroupFormationTool.QuestionManager;

public enum QuestionType {
	Numeric {
		public String toString() {
			return "Numeric";
		}
	},
	MCQONE {
		public String toString() {
			return "Multiple choice choose one";
		}
	},
	MCQMULTIPLE {
		public String toString() {
			return "Multiple choice choose multiple";
		}
	},
	FREETEXT {
		public String toString() {
			return "Free Text";
		}
	}
}
