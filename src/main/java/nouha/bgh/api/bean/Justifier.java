package nouha.bgh.api.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Justifier {
	private int width = 80;

	public String justify(String[] words) {
		StringBuilder fullParagraph = new StringBuilder();
		int index = 0;
		while (index < words.length) {
			List<String> wordsToAdd = getWordsToAdd(words, index);
			int spaceSize = getSpaceSize(wordsToAdd);
			int added = 0;
			for (String word : wordsToAdd) {
				fullParagraph.append(word);
				added++;
				int left = wordsToAdd.size() - added;
				if (left != 0) {
					int x = (int) Math.floor(spaceSize / left) + 1;
					for (int k = 0; k < x; k++) {
						fullParagraph.append(" ");
						if (k != 0) // first space
							spaceSize--;
					}
				}
			}
			fullParagraph.append("\r\n");
			index += wordsToAdd.size();
		}
		return fullParagraph.toString();
	}

	private List<String> getWordsToAdd(String[] words, int start) {
		List<String> wordsToAdd = new ArrayList<String>();
		int size = 0;
		for (int index = start; index < words.length && (size + words[index].length()) <= width; index++) {
			wordsToAdd.add(words[index]);
			size += words[index].length() + 1;
		}
		return wordsToAdd;
	}

	private int getSpaceSize(List<String> words) {
		int size = 0;
		for (String word : words)
			size += word.length() + 1; // we add the word length and 1 for a space
		return width - (size - 1); // we minus 1 of the last space
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
