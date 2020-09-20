/*
 * generated by Xtext 2.18.0.M3
 */
package org.polarsys.capella.scenario.editor.dsl.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.polarsys.capella.scenario.editor.dsl.parser.antlr.internal.InternalTextualScenarioParser;
import org.polarsys.capella.scenario.editor.dsl.services.TextualScenarioGrammarAccess;

public class TextualScenarioParser extends AbstractAntlrParser {

	@Inject
	private TextualScenarioGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalTextualScenarioParser createParser(XtextTokenStream stream) {
		return new InternalTextualScenarioParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "Model";
	}

	public TextualScenarioGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(TextualScenarioGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
