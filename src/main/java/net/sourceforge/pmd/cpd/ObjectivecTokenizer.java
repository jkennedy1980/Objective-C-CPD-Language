package net.sourceforge.pmd.cpd;

import java.io.IOException;
import java.io.StringReader;

import com.deadmeta4.cpd.generated.ObjCParser;
import com.deadmeta4.cpd.generated.Token;
import com.deadmeta4.cpd.generated.TokenMgrError;

public class ObjectivecTokenizer implements Tokenizer{
	
	private boolean loggingEnabled;
	
	public ObjectivecTokenizer(){
		super();
		String loggingEnabledValue = System.getProperty( "ObjC-CPD-LoggingEnabled", "NO" );
		this.loggingEnabled = "YES".equalsIgnoreCase( loggingEnabledValue );
	}

	@Override
	public void tokenize( SourceCode sourceCode, Tokens tokenEntries ) throws IOException {
		
		
		try {
		    if( this.loggingEnabled ) System.out.println( "CPD Processing: " + sourceCode.getFileName() );
			
		    StringBuffer buffer = sourceCode.getCodeBuffer();
			ObjCParser parser = new ObjCParser( new StringReader( buffer.toString() ) );

		    Token currentToken = parser.getNextToken();
		    while( currentToken.image.length() > 0 ){
		    	tokenEntries.add( new TokenEntry( currentToken.image, sourceCode.getFileName(), currentToken.beginLine ) );
		    	currentToken = (Token) parser.getNextToken();
		    }
		    
		    tokenEntries.add( TokenEntry.getEOF() );
		    
		}catch( TokenMgrError err) {
			
			tokenEntries.add( TokenEntry.getEOF() );
			
			 if( this.loggingEnabled ){
				 System.out.println( "CPD Error - Skipping " + sourceCode.getFileName() + " due to parse errors: " + err.getLocalizedMessage() );
				 err.printStackTrace();
			 }
			 
		}catch( Throwable t ){
			
			tokenEntries.add( TokenEntry.getEOF() );
			
			 if( this.loggingEnabled ){
				 System.out.println( "CPD Error - Skipping " + sourceCode.getFileName() + " due to parse errors: " + t.getLocalizedMessage() );
				 t.printStackTrace();
			 }
			 
		}
	}

}
