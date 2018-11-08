package nouha.bgh.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nouha.bgh.api.bean.Justifier;

public class JustifierTest {
	@Test
	public void JustifyTest() {
		Justifier justifier = new Justifier();
		String textToJustify = "These features tend to make legal writing formal. This formality can take the form of long sentences, complex constructions, archaic and hyper-formal vocabulary, and a focus on content to the exclusion of reader needs. Some of this formality in legal writing is necessary and desirable, given the importance of some legal documents and the seriousness of the circumstances in which some legal documents are used. Yet not all formality in legal writing is justified. To the extent that formality produces opacity and imprecision, it is undesirable. To the extent that formality hinders reader comprehension, it is less desirable. In particular, when legal content must be conveyed to nonlawyers, formality should give way to clear communication.";
		String output = justifier.justify(textToJustify.split(" "));
		String[] lines = output.split("\r\n");
		for (String line : lines) {
			assertTrue(line.length() <= justifier.getWidth());
		}
	}
}