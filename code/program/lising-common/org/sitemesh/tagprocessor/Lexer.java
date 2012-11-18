/* The following code was generated by JFlex 1.4.3 on 2/7/11 3:55 PM */

/*
 * This is the definition (lexer.flex) for the auto-generated lexer (Lexer.java) created by JFlex <http://jflex.de/>.
 * To regenerate Lexer.java, run 'mvn jflex:generate' from the sitemesh directory
 * (this will be run automatically on other mvn goals such as 'compile', 'package', etc).
 *
 * @author Joe Walnes
 */

// class headers
package org.sitemesh.tagprocessor;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 2/7/11 3:55 PM from the specification file
 * <tt>/Applications/dev-local/sitemesh/sitemesh3-live/target/checkout/sitemesh/src/main/java/org/sitemesh/tagprocessor/lexer.flex</tt>
 */
final class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 2048;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int ELEMENT = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\10\0\2\23\1\23\2\0\1\23\22\0\1\23\1\2\1\25\4\0"+
    "\1\26\5\0\1\3\1\0\1\17\14\0\1\1\1\24\1\5\1\6"+
    "\1\0\1\11\1\0\1\7\1\10\4\0\1\22\3\0\1\15\2\0"+
    "\1\16\1\0\1\21\1\20\1\12\3\0\1\14\2\0\1\4\1\0"+
    "\1\13\3\0\1\11\1\0\1\7\1\10\4\0\1\22\3\0\1\15"+
    "\2\0\1\16\1\0\1\21\1\20\1\12\3\0\1\14\uff87\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\2\5\6\0\1\11\3\0\1\12\4\0\1\1"+
    "\6\0\1\13\11\0\1\1\17\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[60];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    // Additional methods to add to generated Lexer to aid in error reporting.
    private TagTokenizer.TokenHandler handler;
    public void setHandler(TagTokenizer.TokenHandler handler) { this.handler = handler; }
    public int position() { return yychar; }
    public int length()   { return yylength(); }
    public int line()     { return -1; /*yyline;*/ }   // useful for debugging, but adds overhead
    public int column()   { return -1; /*yycolumn;*/ } // useful for debugging, but adds overhead
    public void resetLexerState() { yybegin(YYINITIAL); }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 118) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public TagTokenizer.Token nextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;


    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          zzInput = zzCMapL[zzInput];

          boolean zzIsFinal = false;
          boolean zzNoLookAhead = false;

          zzForNext: { switch (zzState) {
            case 0:
              switch (zzInput) {
                case 1: zzIsFinal = true; zzState = 3; break zzForNext;
                default: zzIsFinal = true; zzState = 2; break zzForNext;
              }

            case 1:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 5; break zzForNext;
                case 11: zzIsFinal = true; zzState = 6; break zzForNext;
                case 15: zzIsFinal = true; zzNoLookAhead = true; zzState = 7; break zzForNext;
                case 19: zzIsFinal = true; zzState = 8; break zzForNext;
                case 20: zzIsFinal = true; zzNoLookAhead = true; zzState = 9; break zzForNext;
                case 21: zzIsFinal = true; zzState = 10; break zzForNext;
                case 22: zzIsFinal = true; zzState = 11; break zzForNext;
                default: zzIsFinal = true; zzState = 4; break zzForNext;
              }

            case 2:
              switch (zzInput) {
                case 1: break zzForAction;
                default: zzIsFinal = true; break zzForNext;
              }

            case 3:
              switch (zzInput) {
                case 2: zzState = 12; break zzForNext;
                case 6: zzState = 13; break zzForNext;
                case 12: zzState = 14; break zzForNext;
                case 16: zzState = 15; break zzForNext;
                default: break zzForAction;
              }

            case 4:
              switch (zzInput) {
                case 5: 
                case 11: 
                case 15: 
                case 19: 
                case 20: break zzForAction;
                default: zzIsFinal = true; break zzForNext;
              }

            case 6:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 5; break zzForNext;
                case 3: zzState = 16; break zzForNext;
                default: break zzForAction;
              }

            case 8:
              switch (zzInput) {
                case 19: zzIsFinal = true; break zzForNext;
                default: break zzForAction;
              }

            case 10:
              switch (zzInput) {
                case 21: zzIsFinal = true; zzNoLookAhead = true; zzState = 18; break zzForNext;
                default: zzState = 17; break zzForNext;
              }

            case 11:
              switch (zzInput) {
                case 22: zzIsFinal = true; zzNoLookAhead = true; zzState = 18; break zzForNext;
                default: zzState = 19; break zzForNext;
              }

            case 12:
              switch (zzInput) {
                case 3: zzState = 21; break zzForNext;
                case 4: zzIsFinal = true; zzState = 22; break zzForNext;
                default: zzState = 20; break zzForNext;
              }

            case 13:
              switch (zzInput) {
                case 6: zzState = 23; break zzForNext;
                default: break zzForNext;
              }

            case 14:
              switch (zzInput) {
                case 13: zzState = 24; break zzForNext;
                default: break zzForAction;
              }

            case 15:
              switch (zzInput) {
                case 7: zzState = 25; break zzForNext;
                default: break zzForAction;
              }

            case 16:
              switch (zzInput) {
                case 3: zzState = 26; break zzForNext;
                default: break zzForAction;
              }

            case 17:
              switch (zzInput) {
                case 21: zzIsFinal = true; zzNoLookAhead = true; zzState = 18; break zzForNext;
                default: break zzForNext;
              }

            case 19:
              switch (zzInput) {
                case 22: zzIsFinal = true; zzNoLookAhead = true; zzState = 18; break zzForNext;
                default: break zzForNext;
              }

            case 20:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 27; break zzForNext;
                default: break zzForNext;
              }

            case 21:
              switch (zzInput) {
                case 3: zzState = 28; break zzForNext;
                default: break zzForAction;
              }

            case 22:
              switch (zzInput) {
                case 7: zzState = 29; break zzForNext;
                default: break zzForAction;
              }

            case 23:
              switch (zzInput) {
                case 6: break zzForNext;
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 27; break zzForNext;
                default: zzState = 13; break zzForNext;
              }

            case 24:
              switch (zzInput) {
                case 14: zzState = 30; break zzForNext;
                default: break zzForAction;
              }

            case 25:
              switch (zzInput) {
                case 17: zzState = 31; break zzForNext;
                default: break zzForAction;
              }

            case 26:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 5; break zzForNext;
                default: break zzForAction;
              }

            case 28:
              switch (zzInput) {
                case 3: zzState = 33; break zzForNext;
                case 4: zzIsFinal = true; zzNoLookAhead = true; zzState = 34; break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 29:
              switch (zzInput) {
                case 8: zzState = 35; break zzForNext;
                default: break zzForAction;
              }

            case 30:
              switch (zzInput) {
                case 1: zzState = 36; break zzForNext;
                default: break zzForNext;
              }

            case 31:
              switch (zzInput) {
                case 18: zzState = 37; break zzForNext;
                default: break zzForAction;
              }

            case 32:
              switch (zzInput) {
                case 3: zzState = 38; break zzForNext;
                default: break zzForNext;
              }

            case 33:
              switch (zzInput) {
                case 3: zzState = 39; break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 35:
              switch (zzInput) {
                case 9: zzState = 40; break zzForNext;
                default: break zzForAction;
              }

            case 36:
              switch (zzInput) {
                case 1: break zzForNext;
                case 15: zzState = 41; break zzForNext;
                default: zzState = 30; break zzForNext;
              }

            case 37:
              switch (zzInput) {
                case 14: zzState = 42; break zzForNext;
                default: break zzForAction;
              }

            case 38:
              switch (zzInput) {
                case 3: zzState = 43; break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 39:
              switch (zzInput) {
                case 3: zzState = 43; break zzForNext;
                case 5: zzIsFinal = true; zzState = 44; break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 40:
              switch (zzInput) {
                case 10: zzState = 45; break zzForNext;
                default: break zzForAction;
              }

            case 41:
              switch (zzInput) {
                case 1: zzState = 36; break zzForNext;
                case 12: zzState = 46; break zzForNext;
                default: zzState = 30; break zzForNext;
              }

            case 42:
              switch (zzInput) {
                case 10: zzState = 47; break zzForNext;
                default: break zzForAction;
              }

            case 43:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 27; break zzForNext;
                case 3: break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 44:
              switch (zzInput) {
                case 3: zzState = 38; break zzForNext;
                default: zzState = 32; break zzForNext;
              }

            case 45:
              switch (zzInput) {
                case 9: zzState = 48; break zzForNext;
                default: break zzForAction;
              }

            case 46:
              switch (zzInput) {
                case 1: zzState = 36; break zzForNext;
                case 13: zzState = 49; break zzForNext;
                default: zzState = 30; break zzForNext;
              }

            case 47:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                default: break zzForNext;
              }

            case 48:
              switch (zzInput) {
                case 4: zzState = 51; break zzForNext;
                default: break zzForAction;
              }

            case 49:
              switch (zzInput) {
                case 14: zzState = 20; break zzForNext;
                case 1: zzState = 36; break zzForNext;
                default: zzState = 30; break zzForNext;
              }

            case 50:
              switch (zzInput) {
                case 1: break zzForNext;
                case 15: zzState = 52; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 51:
              switch (zzInput) {
                case 11: zzState = 53; break zzForNext;
                default: break zzForNext;
              }

            case 52:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                case 16: zzState = 54; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 53:
              switch (zzInput) {
                case 11: zzState = 55; break zzForNext;
                default: zzState = 51; break zzForNext;
              }

            case 54:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                case 7: zzState = 56; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 55:
              switch (zzInput) {
                case 5: zzIsFinal = true; zzNoLookAhead = true; zzState = 27; break zzForNext;
                case 11: break zzForNext;
                default: zzState = 51; break zzForNext;
              }

            case 56:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                case 17: zzState = 57; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 57:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                case 18: zzState = 58; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 58:
              switch (zzInput) {
                case 1: zzState = 50; break zzForNext;
                case 14: zzState = 59; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            case 59:
              switch (zzInput) {
                case 10: zzState = 20; break zzForNext;
                case 1: zzState = 50; break zzForNext;
                default: zzState = 47; break zzForNext;
              }

            default:
              // if this is ever reached, there is a serious bug in JFlex
              zzScanError(ZZ_UNKNOWN_ERROR);
              break;
          } }

          if ( zzIsFinal ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( zzNoLookAhead ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 10: 
          { yybegin(ELEMENT); return TagTokenizer.Token.LT_CLOSE_MAGIC_COMMENT;
          }
        case 12: break;
        case 5: 
          { handler.warning("Illegal character <"+ yytext() +">",
                              line(), column()); return TagTokenizer.Token.TEXT;
          }
        case 13: break;
        case 6: 
          { return TagTokenizer.Token.SLASH;
          }
        case 14: break;
        case 4: 
          { yybegin(YYINITIAL); return TagTokenizer.Token.GT;
          }
        case 15: break;
        case 11: 
          { yybegin(ELEMENT); return TagTokenizer.Token.LT_OPEN_MAGIC_COMMENT;
          }
        case 16: break;
        case 1: 
          { return TagTokenizer.Token.TEXT;
          }
        case 17: break;
        case 9: 
          { return TagTokenizer.Token.QUOTED;
          }
        case 18: break;
        case 8: 
          { return TagTokenizer.Token.EQUALS;
          }
        case 19: break;
        case 7: 
          { return TagTokenizer.Token.WHITESPACE;
          }
        case 20: break;
        case 3: 
          { return TagTokenizer.Token.WORD;
          }
        case 21: break;
        case 2: 
          { yybegin(ELEMENT); return TagTokenizer.Token.LT;
          }
        case 22: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {
                return TagTokenizer.Token.EOF;
              }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
