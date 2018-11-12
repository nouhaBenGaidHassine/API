package nouha.bgh.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nouha.bgh.api.bean.Justifier;

public class JustifierTest {
	@Test
	public void JustifyTest() {
		Justifier justifier = new Justifier();
		String textToJustify = "Longtemps, je me suis couch� de bonne heure. Parfois, � peine ma bougie �teinte, mes yeux se fermaient si vite que je n�avais pas le temps de me dire: �Je m�endors.� Et, une demi-heure apr�s, la pens�e qu�il �tait temps de chercher le sommeil m��veillait; je voulais poser le volume que je croyais avoir dans les mains et souffler ma lumi�re; je n�avais pas cess� en dormant de faire des r�flexions sur ce que je venais de lire, mais ces r�flexions avaient pris un tour un peu particulier; il me semblait que j��tais moi-m�me ce dont parlait l�ouvrage: une �glise, un quatuor, la rivalit� de Fran�ois Ier et de Charles-Quint. \r\n" + 
				"\r\n" + 
				"Cette croyance survivait pendant quelques secondes � mon r�veil; elle ne choquait pas ma raison, mais pesait comme des �cailles sur mes yeux et les emp�chait de se rendre compte que le bougeoir n��tait plus allum�. \r\n" + 
				" Puis elle commen�ait � me devenir inintelligible, comme apr�s la m�tempsycose les pens�es d�une existence ant�rieure; le sujet du livre se d�tachait de moi, j��tais libre de m�y appliquer ou non; aussit�t je recouvrais la vue et j��tais bien �tonn� de trouver autour de moi une obscurit�, douce et reposante pour mes yeux, mais peut-�tre plus encore pour mon esprit, � qui elle apparaissait comme une chose sans cause, incompr�hensible, comme une chose vraiment obscure. Je me demandais quelle heure il pouvait �tre; j�entendais le sifflement des trains qui, plus ou moins �loign�, comme le chant d�un oiseau dans une for�t, relevant les distances, me d�crivait l��tendue de la campagne d�serte o� le voyageur se h�te vers la station prochaine; et le petit chemin qu�il suit va �tre grav� dans son souvenir par l�excitation qu�il doit � des lieux nouveaux, � des actes inaccoutum�s, � la causerie r�cente et aux adieux sous la lampe �trang�re qui le suivent encore dans le silence de la nuit, � la douceur prochaine du retour.";
		String output = justifier.justify(textToJustify);

		String[] lines = output.split(System.getProperty("line.separator"));
		for (String line : lines) {
			assertTrue(line.length() <= Justifier.width);
		}
	}
}