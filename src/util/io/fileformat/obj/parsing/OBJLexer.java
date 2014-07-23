/* The following code was generated by JFlex 1.5.0-SNAPSHOT */

package util.io.fileformat.obj.parsing;
import java.io.File;
import java.io.Reader;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.5.0-SNAPSHOT
 * from the specification file <tt>obj.flex</tt>
 */
class OBJLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 2;

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
    "\11\0\1\45\1\40\1\41\1\41\1\37\22\0\1\45\2\0\1\42"+
    "\7\0\1\1\1\0\1\1\1\5\1\43\1\3\1\2\1\31\7\2"+
    "\7\0\32\6\1\0\1\44\2\0\1\34\1\0\1\21\1\24\1\25"+
    "\1\22\1\23\1\30\1\14\1\33\1\32\1\36\1\6\1\27\1\16"+
    "\1\12\1\17\1\13\1\6\1\20\1\15\1\11\1\7\1\10\1\35"+
    "\1\6\1\26\1\6\12\0\1\41\44\0\1\6\12\0\1\6\4\0"+
    "\1\6\5\0\27\6\1\0\37\6\1\0\u01ca\6\4\0\14\6\16\0"+
    "\5\6\7\0\1\6\1\0\1\6\21\0\165\6\1\0\2\6\2\0"+
    "\4\6\10\0\1\6\1\0\3\6\1\0\1\6\1\0\24\6\1\0"+
    "\123\6\1\0\213\6\1\0\245\6\11\0\46\6\2\0\1\6\7\0"+
    "\47\6\11\0\55\6\1\0\1\6\1\0\2\6\1\0\2\6\1\0"+
    "\1\6\10\0\33\6\5\0\3\6\35\0\13\6\5\0\100\6\12\4"+
    "\4\0\146\6\1\0\10\6\2\0\12\6\1\0\6\6\12\4\3\6"+
    "\2\0\1\6\20\0\73\6\2\0\145\6\16\0\12\4\54\6\4\0"+
    "\1\6\5\0\56\6\22\0\34\6\104\0\1\6\1\0\13\6\67\0"+
    "\33\6\1\0\144\6\2\0\12\4\1\0\7\6\1\0\7\6\1\0"+
    "\3\6\1\0\10\6\2\0\2\6\2\0\26\6\1\0\7\6\1\0"+
    "\1\6\3\0\4\6\2\0\11\6\2\0\2\6\2\0\4\6\10\0"+
    "\1\6\4\0\2\6\1\0\5\6\2\0\12\4\2\6\17\0\3\6"+
    "\1\0\6\6\4\0\2\6\2\0\26\6\1\0\7\6\1\0\2\6"+
    "\1\0\2\6\1\0\2\6\2\0\1\6\1\0\5\6\4\0\2\6"+
    "\2\0\3\6\3\0\1\6\7\0\4\6\1\0\1\6\7\0\12\4"+
    "\6\6\13\0\3\6\1\0\11\6\1\0\3\6\1\0\26\6\1\0"+
    "\7\6\1\0\2\6\1\0\5\6\2\0\12\6\1\0\3\6\1\0"+
    "\3\6\2\0\1\6\17\0\4\6\2\0\12\4\21\0\3\6\1\0"+
    "\10\6\2\0\2\6\2\0\26\6\1\0\7\6\1\0\2\6\1\0"+
    "\5\6\2\0\11\6\2\0\2\6\2\0\3\6\10\0\2\6\4\0"+
    "\2\6\1\0\5\6\2\0\12\4\1\0\1\6\20\0\2\6\1\0"+
    "\6\6\3\0\3\6\1\0\4\6\3\0\2\6\1\0\1\6\1\0"+
    "\2\6\3\0\2\6\3\0\3\6\3\0\14\6\4\0\5\6\3\0"+
    "\3\6\1\0\4\6\2\0\1\6\6\0\1\6\16\0\12\4\21\0"+
    "\3\6\1\0\10\6\1\0\3\6\1\0\27\6\1\0\12\6\1\0"+
    "\5\6\3\0\10\6\1\0\3\6\1\0\4\6\7\0\2\6\1\0"+
    "\2\6\6\0\4\6\2\0\12\4\22\0\2\6\1\0\10\6\1\0"+
    "\3\6\1\0\27\6\1\0\12\6\1\0\5\6\2\0\11\6\1\0"+
    "\3\6\1\0\4\6\7\0\2\6\7\0\1\6\1\0\4\6\2\0"+
    "\12\4\1\0\2\6\17\0\2\6\1\0\10\6\1\0\3\6\1\0"+
    "\51\6\2\0\10\6\1\0\3\6\1\0\5\6\10\0\1\6\10\0"+
    "\4\6\2\0\12\4\12\0\6\6\2\0\2\6\1\0\22\6\3\0"+
    "\30\6\1\0\11\6\1\0\1\6\2\0\7\6\3\0\1\6\4\0"+
    "\6\6\1\0\1\6\1\0\10\6\22\0\2\6\15\0\72\6\5\0"+
    "\17\6\1\0\12\4\47\0\2\6\1\0\1\6\2\0\2\6\1\0"+
    "\1\6\2\0\1\6\6\0\4\6\1\0\7\6\1\0\3\6\1\0"+
    "\1\6\1\0\1\6\2\0\2\6\1\0\15\6\1\0\3\6\2\0"+
    "\5\6\1\0\1\6\1\0\6\6\2\0\12\4\2\0\4\6\40\0"+
    "\1\6\27\0\2\6\6\0\12\4\13\0\1\6\1\0\1\6\1\0"+
    "\1\6\4\0\12\6\1\0\44\6\4\0\24\6\1\0\22\6\1\0"+
    "\44\6\11\0\1\6\71\0\100\6\12\4\6\0\100\6\12\4\4\6"+
    "\2\0\46\6\1\0\1\6\5\0\1\6\2\0\53\6\1\0\u014d\6"+
    "\1\0\4\6\2\0\7\6\1\0\1\6\1\0\4\6\2\0\51\6"+
    "\1\0\4\6\2\0\41\6\1\0\4\6\2\0\7\6\1\0\1\6"+
    "\1\0\4\6\2\0\17\6\1\0\71\6\1\0\4\6\2\0\103\6"+
    "\2\0\3\6\40\0\20\6\20\0\125\6\14\0\u026c\6\2\0\21\6"+
    "\1\0\32\6\5\0\113\6\3\0\3\6\17\0\15\6\1\0\7\6"+
    "\13\0\25\6\13\0\24\6\14\0\15\6\1\0\3\6\1\0\2\6"+
    "\14\0\124\6\3\0\1\6\4\0\2\6\2\0\12\4\41\0\3\6"+
    "\2\0\12\4\6\0\130\6\10\0\53\6\5\0\106\6\12\0\35\6"+
    "\3\0\14\6\4\0\14\6\12\0\12\4\36\6\2\0\5\6\13\0"+
    "\54\6\4\0\32\6\6\0\12\4\46\0\34\6\4\0\77\6\1\0"+
    "\35\6\2\0\1\6\12\4\6\0\12\4\15\0\1\6\130\0\114\6"+
    "\4\0\12\4\21\0\11\6\14\0\60\6\12\4\72\6\14\0\70\6"+
    "\10\0\12\4\3\0\3\6\12\4\44\6\122\0\3\6\1\0\43\6"+
    "\11\0\347\6\25\0\u011a\6\2\0\6\6\2\0\46\6\2\0\6\6"+
    "\2\0\10\6\1\0\1\6\1\0\1\6\1\0\1\6\1\0\37\6"+
    "\2\0\65\6\1\0\7\6\1\0\1\6\3\0\3\6\1\0\7\6"+
    "\3\0\4\6\2\0\6\6\4\0\15\6\5\0\3\6\1\0\7\6"+
    "\53\0\1\41\1\41\25\0\2\6\23\0\1\6\34\0\1\6\15\0"+
    "\1\6\20\0\15\6\63\0\41\6\21\0\1\6\4\0\1\6\2\0"+
    "\12\6\1\0\1\6\3\0\5\6\6\0\1\6\1\0\1\6\1\0"+
    "\1\6\1\0\4\6\1\0\13\6\2\0\4\6\5\0\5\6\4\0"+
    "\1\6\21\0\51\6\u032d\0\64\6\u0716\0\57\6\1\0\57\6\1\0"+
    "\205\6\6\0\11\6\14\0\46\6\1\0\1\6\5\0\1\6\2\0"+
    "\70\6\7\0\1\6\17\0\30\6\11\0\7\6\1\0\7\6\1\0"+
    "\7\6\1\0\7\6\1\0\7\6\1\0\7\6\1\0\7\6\1\0"+
    "\7\6\1\0\40\6\57\0\1\6\u01d5\0\3\6\31\0\17\6\1\0"+
    "\5\6\2\0\5\6\4\0\126\6\2\0\2\6\2\0\3\6\1\0"+
    "\132\6\1\0\4\6\5\0\51\6\3\0\136\6\21\0\33\6\65\0"+
    "\20\6\u0200\0\u19b6\6\112\0\u51cd\6\63\0\u048d\6\103\0\56\6\2\0"+
    "\u010d\6\3\0\20\6\12\4\2\6\24\0\63\6\1\0\12\6\1\0"+
    "\31\6\7\0\123\6\45\0\11\6\2\0\147\6\2\0\4\6\1\0"+
    "\4\6\14\0\13\6\115\0\60\6\30\0\64\6\14\0\105\6\13\0"+
    "\12\4\6\0\30\6\3\0\1\6\4\0\12\4\44\6\2\0\44\6"+
    "\14\0\35\6\3\0\101\6\16\0\1\6\12\4\46\0\67\6\11\0"+
    "\16\6\2\0\12\4\6\0\27\6\3\0\2\6\4\0\103\6\30\0"+
    "\3\6\2\0\20\6\2\0\5\6\12\0\6\6\2\0\6\6\2\0"+
    "\6\6\11\0\7\6\1\0\7\6\221\0\53\6\1\0\2\6\2\0"+
    "\12\4\6\0\u2ba4\6\14\0\27\6\4\0\61\6\u2104\0\u016e\6\2\0"+
    "\152\6\46\0\7\6\14\0\5\6\5\0\14\6\1\0\15\6\1\0"+
    "\5\6\1\0\1\6\1\0\2\6\1\0\2\6\1\0\154\6\41\0"+
    "\u016b\6\22\0\100\6\2\0\66\6\50\0\14\6\4\0\20\6\20\0"+
    "\7\6\14\0\2\6\30\0\3\6\40\0\5\6\1\0\207\6\23\0"+
    "\12\4\7\0\32\6\4\0\1\6\1\0\32\6\13\0\131\6\3\0"+
    "\6\6\2\0\6\6\2\0\6\6\2\0\3\6\43\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\2\1\1\2\2\3\1\4\1\5\1\3\1\6"+
    "\1\7\1\10\1\3\1\11\5\3\1\12\1\13\1\3"+
    "\2\14\1\15\1\16\1\1\1\17\2\20\1\2\1\0"+
    "\2\3\1\21\1\22\1\23\4\3\1\24\3\3\1\25"+
    "\15\3\2\26\1\0\1\27\11\3\1\30\1\31\1\3"+
    "\1\32\5\3\1\33\1\3\1\34\1\3\1\27\2\3"+
    "\1\35\1\36\1\37\1\40\1\3\1\41\3\3\1\42"+
    "\1\3\1\43\3\3\1\44\2\3\1\45\3\3\1\46"+
    "\1\47\1\50\2\3\1\51\2\3\1\52\1\3\1\53"+
    "\7\3\1\54\1\55\1\56\1\3\1\57";

  private static int [] zzUnpackAction() {
    int [] result = new int[134];
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


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\46\0\114\0\162\0\230\0\276\0\344\0\u010a"+
    "\0\u0130\0\u0156\0\u017c\0\344\0\u01a2\0\u01c8\0\344\0\u01ee"+
    "\0\u0214\0\u023a\0\u0260\0\u0286\0\u02ac\0\344\0\u02d2\0\u02f8"+
    "\0\114\0\114\0\114\0\u031e\0\114\0\u0344\0\114\0\u036a"+
    "\0\u0390\0\u03b6\0\u03dc\0\344\0\344\0\344\0\u0402\0\u0428"+
    "\0\u044e\0\u0474\0\344\0\u049a\0\u04c0\0\u04e6\0\344\0\u050c"+
    "\0\u0532\0\u0558\0\u057e\0\u05a4\0\u05ca\0\u05f0\0\u0616\0\u063c"+
    "\0\u0662\0\u0688\0\u06ae\0\u06d4\0\u06fa\0\114\0\u0720\0\u03b6"+
    "\0\u0746\0\u076c\0\u0792\0\u07b8\0\u07de\0\u0804\0\u082a\0\u0850"+
    "\0\u0876\0\344\0\344\0\u089c\0\344\0\u08c2\0\u08e8\0\u090e"+
    "\0\u0934\0\u095a\0\344\0\u0980\0\344\0\u09a6\0\u0720\0\u09cc"+
    "\0\u09f2\0\344\0\344\0\344\0\344\0\u0a18\0\344\0\u0a3e"+
    "\0\u0a64\0\u0a8a\0\344\0\u0ab0\0\u0ad6\0\u0afc\0\u0b22\0\u0b48"+
    "\0\344\0\u0b6e\0\u0b94\0\344\0\u0bba\0\u0be0\0\u0c06\0\344"+
    "\0\344\0\344\0\u0c2c\0\u0c52\0\344\0\u0c78\0\u0c9e\0\344"+
    "\0\u0cc4\0\344\0\u0cea\0\u0d10\0\u0d36\0\u0d5c\0\u0d82\0\u0da8"+
    "\0\u0dce\0\344\0\344\0\344\0\u0df4\0\344";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\2\6\2\7\1\10\1\11\1\12"+
    "\1\7\1\13\1\14\1\15\1\16\1\17\1\20\1\7"+
    "\1\21\1\22\1\23\1\24\1\7\1\25\1\26\1\5"+
    "\1\7\1\27\3\7\1\30\1\31\1\0\1\32\1\33"+
    "\1\34\40\35\1\36\1\37\1\0\4\35\50\0\1\40"+
    "\2\41\24\0\1\40\16\0\2\5\1\6\1\42\23\7"+
    "\1\5\5\7\11\0\3\6\1\42\23\7\1\6\5\7"+
    "\11\0\35\7\11\0\13\7\1\43\21\7\11\0\7\7"+
    "\1\44\1\45\1\46\23\7\11\0\16\7\1\47\16\7"+
    "\11\0\17\7\1\50\15\7\11\0\5\7\1\51\1\7"+
    "\1\52\1\7\1\53\11\7\1\54\5\7\1\55\3\7"+
    "\11\0\7\7\1\56\2\7\1\57\22\7\11\0\17\7"+
    "\1\60\15\7\11\0\21\7\1\61\10\7\1\62\2\7"+
    "\11\0\10\7\1\63\24\7\11\0\14\7\1\64\4\7"+
    "\1\65\13\7\11\0\5\7\1\66\1\7\1\67\3\7"+
    "\1\70\1\7\1\71\14\7\1\72\2\7\11\0\15\7"+
    "\1\73\17\7\11\0\15\7\1\74\17\7\47\0\1\31"+
    "\44\0\1\75\1\76\45\0\1\37\7\0\2\40\1\41"+
    "\1\77\23\0\1\40\16\0\3\41\1\77\23\0\1\41"+
    "\16\0\2\100\25\7\1\100\5\7\11\0\21\7\1\101"+
    "\13\7\11\0\17\7\1\102\10\7\1\103\4\7\11\0"+
    "\16\7\1\104\16\7\11\0\16\7\1\105\16\7\11\0"+
    "\21\7\1\106\13\7\11\0\16\7\1\107\16\7\11\0"+
    "\17\7\1\110\15\7\11\0\25\7\1\111\7\7\11\0"+
    "\7\7\1\112\25\7\11\0\12\7\1\113\22\7\11\0"+
    "\30\7\1\114\4\7\11\0\20\7\1\115\14\7\11\0"+
    "\17\7\1\116\15\7\11\0\6\7\1\117\26\7\11\0"+
    "\16\7\1\120\16\7\11\0\21\7\1\121\13\7\11\0"+
    "\7\7\1\122\25\7\11\0\10\7\1\123\24\7\11\0"+
    "\30\7\1\124\4\7\11\0\20\7\1\125\14\7\11\0"+
    "\25\7\1\126\7\7\47\0\1\76\7\0\2\127\25\0"+
    "\1\127\16\0\14\7\1\130\20\7\11\0\23\7\1\131"+
    "\11\7\11\0\14\7\1\132\20\7\11\0\14\7\1\133"+
    "\20\7\11\0\26\7\1\134\6\7\11\0\11\7\1\135"+
    "\11\7\1\136\11\7\11\0\6\7\1\137\26\7\11\0"+
    "\20\7\1\140\14\7\11\0\25\7\1\141\7\7\11\0"+
    "\10\7\1\142\24\7\11\0\7\7\1\143\25\7\11\0"+
    "\21\7\1\144\13\7\11\0\6\7\1\145\26\7\11\0"+
    "\23\7\1\146\11\7\11\0\24\7\1\147\10\7\11\0"+
    "\10\7\1\150\24\7\11\0\21\7\1\151\13\7\11\0"+
    "\7\7\1\152\25\7\11\0\21\7\1\153\13\7\11\0"+
    "\31\7\1\154\3\7\11\0\15\7\1\155\17\7\11\0"+
    "\30\7\1\156\4\7\11\0\7\7\1\157\25\7\11\0"+
    "\25\7\1\160\7\7\11\0\27\7\1\161\5\7\11\0"+
    "\31\7\1\162\3\7\11\0\11\7\1\163\23\7\11\0"+
    "\7\7\1\164\25\7\11\0\25\7\1\165\7\7\11\0"+
    "\32\7\1\166\2\7\11\0\33\7\1\167\1\7\11\0"+
    "\22\7\1\170\12\7\11\0\21\7\1\171\13\7\11\0"+
    "\21\7\1\172\13\7\11\0\21\7\1\173\13\7\11\0"+
    "\15\7\1\174\17\7\11\0\32\7\1\175\2\7\11\0"+
    "\16\7\1\176\16\7\11\0\16\7\1\177\16\7\11\0"+
    "\22\7\1\200\12\7\11\0\15\7\1\201\17\7\11\0"+
    "\11\7\1\202\23\7\11\0\11\7\1\203\23\7\11\0"+
    "\34\7\1\204\11\0\22\7\1\205\12\7\11\0\34\7"+
    "\1\206\7\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3610];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
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

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\25\1\3\11\1\1\1\11\1\1\1\11"+
    "\1\1\1\0\34\1\1\11\1\0\107\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[134];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
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
	private OBJParser yyparser;
	private String str;

	public OBJLexer(Reader r, OBJParser yyparser) {
		this(r);
		this.yyparser = yyparser;
	}
  
	public int line(){
		return yyline + 1;
	}
  
	public int pos(){
		return yychar + 1;
	}
  
  


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  OBJLexer(java.io.Reader in) {
    this.zzReader = in;
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
    while (i < 1856) {
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
   * Internal scan buffer is resized down to its initial length, if it has grown.
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
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
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
  public int yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


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
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { System.err.println("Unexpected ["+ yytext() + "] at line " + line() + ":" + pos() + ".");
          }
        case 48: break;
        case 2: 
          { yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(Integer.parseInt(yytext())); return OBJParser.INT;
          }
        case 49: break;
        case 3: 
          { yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(yytext()); return OBJParser.STRING;
          }
        case 50: break;
        case 4: 
          { yychar += yytext().length(); return OBJParser.U;
          }
        case 51: break;
        case 5: 
          { yychar += yytext().length(); return OBJParser.VERTEX;
          }
        case 52: break;
        case 6: 
          { yychar += yytext().length(); return OBJParser.POINT;
          }
        case 53: break;
        case 7: 
          { yychar += yytext().length(); return OBJParser.GROUP;
          }
        case 54: break;
        case 8: 
          { yychar += yytext().length(); return OBJParser.SMOOTH;
          }
        case 55: break;
        case 9: 
          { yychar += yytext().length(); return OBJParser.OBJECT;
          }
        case 56: break;
        case 10: 
          { yychar += yytext().length(); return OBJParser.LINE;
          }
        case 57: break;
        case 11: 
          { yychar += yytext().length(); return OBJParser.FACE;
          }
        case 58: break;
        case 12: 
          { yyline++; yychar = 0; return OBJParser.NL;
          }
        case 59: break;
        case 13: 
          { yychar++; yybegin(COMMENT);
          }
        case 60: break;
        case 14: 
          { yychar++; return OBJParser.SLASH;
          }
        case 61: break;
        case 15: 
          { yychar++;
          }
        case 62: break;
        case 16: 
          { yyline++; yychar = 0; yybegin(YYINITIAL); return OBJParser.NL;
          }
        case 63: break;
        case 17: 
          { yychar += yytext().length(); return OBJParser.TEXTURE;
          }
        case 64: break;
        case 18: 
          { yychar += yytext().length(); return OBJParser.NORMAL;
          }
        case 65: break;
        case 19: 
          { yychar += yytext().length(); return OBJParser.PARAMETER;
          }
        case 66: break;
        case 20: 
          { yychar += yytext().length(); return OBJParser.SPECPOINT;
          }
        case 67: break;
        case 21: 
          { yychar += yytext().length(); return OBJParser.MERGE;
          }
        case 68: break;
        case 22: 
          { yyline++; yychar = 0;
          }
        case 69: break;
        case 23: 
          { yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(Float.parseFloat(yytext())); return OBJParser.FLOAT;
          }
        case 70: break;
        case 24: 
          { yychar += yytext().length(); return OBJParser.RAT;
          }
        case 71: break;
        case 25: 
          { yychar += yytext().length(); return OBJParser.DEGREE;
          }
        case 72: break;
        case 26: 
          { yychar += yytext().length(); return OBJParser.END;
          }
        case 73: break;
        case 27: 
          { yychar += yytext().length(); return OBJParser.CONNECT;
          }
        case 74: break;
        case 28: 
          { yychar += yytext().length(); return OBJParser.LOD;
          }
        case 75: break;
        case 29: 
          { yychar += yytext().length(); return OBJParser.TRIMOUT;
          }
        case 76: break;
        case 30: 
          { yychar += yytext().length(); return OBJParser.PARAM;
          }
        case 77: break;
        case 31: 
          { yychar += yytext().length(); return OBJParser.SURFACE;
          }
        case 78: break;
        case 32: 
          { yychar += yytext().length(); return OBJParser.STEP;
          }
        case 79: break;
        case 33: 
          { yychar += yytext().length(); return OBJParser.SPECCURVE;
          }
        case 80: break;
        case 34: 
          { yychar += yytext().length(); return OBJParser.BASIS;
          }
        case 81: break;
        case 35: 
          { yychar += yytext().length(); return OBJParser.CURVE;
          }
        case 82: break;
        case 36: 
          { yychar += yytext().length(); return OBJParser.TRIMIN;
          }
        case 83: break;
        case 37: 
          { yychar += yytext().length(); return OBJParser.SURFACEAPPROX;
          }
        case 84: break;
        case 38: 
          { yychar += yytext().length(); return OBJParser.BEVEL;
          }
        case 85: break;
        case 39: 
          { yychar += yytext().length(); return OBJParser.CURVE2D;
          }
        case 86: break;
        case 40: 
          { yychar += yytext().length(); return OBJParser.CURVEAPPROX;
          }
        case 87: break;
        case 41: 
          { yychar += yytext().length(); return OBJParser.MATERIAL;
          }
        case 88: break;
        case 42: 
          { yychar += yytext().length(); return OBJParser.LIBRARY;
          }
        case 89: break;
        case 43: 
          { yychar += yytext().length(); return OBJParser.CSTYPE;
          }
        case 90: break;
        case 44: 
          { yychar += yytext().length(); return OBJParser.DINTER;
          }
        case 91: break;
        case 45: 
          { yychar += yytext().length(); return OBJParser.CINTER;
          }
        case 92: break;
        case 46: 
          { yychar += yytext().length(); return OBJParser.RAYTRACE;
          }
        case 93: break;
        case 47: 
          { yychar += yytext().length(); return OBJParser.SHADOW;
          }
        case 94: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return 0; }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
