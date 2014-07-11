package util.ui.swing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class LAFUtil {

	public static final ArrayList<LAF> lafs = new ArrayList<LAF>();

	static {
		registerLAF(BasicLookAndFeel.class, "Basic");
		registerLAF(MetalLookAndFeel.class, "Metal");
		registerLAF(SynthLookAndFeel.class, "Synth");
	}

	public static void registerLAF(Class<? extends LookAndFeel> laf, String prefix) {
		lafs.add(new LAF(laf, prefix));
	}

	@SuppressWarnings("unchecked")
	public static void installUI(Class<? extends JComponent> clazz) {
		String uiClassID = getUIClassID(clazz);
		if (uiClassID == null)
			return;

		LAF laf = getValidLAF(UIManager.getLookAndFeel().getClass(), uiClassID);
		if (laf == null)
			return;
		
		Class<? extends ComponentUI> delegate = getDelegate(laf.getQualifiedDelegateName(uiClassID));
		if (delegate == null)
			return;
		
		installDelegate(laf, uiClassID);
		installDefaults(delegate, UIManager.getLookAndFeelDefaults());
		
		System.out.println(clazz.getName() + " installed under " + laf.laf.getName() + ".");
	}

	private static String getUIClassID(Class<? extends JComponent> clazz) {
		try {
			Field uiClassID = clazz.getDeclaredField("uiClassID");
			uiClassID.setAccessible(true);
			return (String) uiClassID.get(null);
		} catch (NoSuchFieldException e) {
			System.err.println("Failed to install delegate for " + clazz.getCanonicalName() + ". Does not have uiClassID field.");
		} catch (SecurityException e) {
			System.err.println("Failed to install delegate for " + clazz.getCanonicalName() + ". Could not access uiClassID field.");
		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.err.println("Failed to install delegate for " + clazz.getCanonicalName() + ". uiClassID field is not static or cannot be accessed.");
		} catch (ClassCastException e) {
			System.err.println("Failed to install delegate for " + clazz.getCanonicalName() + ". uiClassID field is not a String.");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Class<? extends ComponentUI> getDelegate(String qualifiedDelegateName) {
		try {
			return (Class<? extends ComponentUI>) Class.forName(qualifiedDelegateName);
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to initialize delegate defaults.");
			return null;
		} catch (ClassCastException e) {
			System.err.println("Failed to initialize delegate defaults. Delegate does not extend ComponentUI.");
			return null;
		}

	}

	private static void installDelegate(LAF laf, String uiClassID) {
		UIManager.getLookAndFeelDefaults().put(uiClassID, laf.getQualifiedDelegateName(uiClassID));
	}

	private static void installDefaults(Class<? extends ComponentUI> clazz, UIDefaults defaults) {
		try {
			clazz.getMethod("installLAF", UIDefaults.class).invoke(null, defaults);
		} catch (NoSuchMethodException e) {
			System.err.println("Failed to initialize delegate defaults for " + clazz.getCanonicalName() + ". installLAF(UIDefaults) method does not exist.");
		} catch (SecurityException e) {
			System.err.println("Failed to initialize delegate defaults for " + clazz.getCanonicalName() + ". installLAF(UIDefaults) cannot be accessed.");
		} catch (IllegalAccessException e) {
			System.err.println("Failed to initialize delegate defaults for " + clazz.getCanonicalName() + ". installLAF(UIDefaults) cannot be invoked.");
		} catch (IllegalArgumentException e) {
			System.err.println("Failed to initialize delegate defaults for " + clazz.getCanonicalName()
					+ ". Illegal Arguments provided to installLAF(UIDefaults).");
		} catch (InvocationTargetException e) {
			System.err.println("Failed to initialize delegate defaults for " + clazz.getCanonicalName() + ". installLAF(UIDefaults) invocation failed.");
		}
	}

	/**/

	private static LAF getValidLAF(Class<? extends LookAndFeel> lafClass, String uiClassID) {
		for (LAF laf : lafs) {
			if (laf.laf == lafClass && validLAF(laf, uiClassID))
				return laf;
		}
		try {
			@SuppressWarnings("unchecked")
			Class<? extends LookAndFeel> lafParent = (Class<? extends LookAndFeel>) lafClass.getSuperclass();
			return getValidLAF(lafParent, uiClassID);
		} catch (ClassCastException e) {
			System.err.println("Failed to install delegate for " + lafClass.getCanonicalName() + ". Could not find valid delegate for the Look and Feel.");
			return null;
		}
	}

	private static boolean validLAF(LAF laf, String uiClassID) {
		try {
			@SuppressWarnings({ "unchecked", "unused" })
			Class<? extends ComponentUI> uiClass = (Class<? extends ComponentUI>) Class.forName(laf.getQualifiedDelegateName(uiClassID));
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (ClassCastException e) {
			return false;
		}
	}

	/**/

	private static class LAF {
		public final Class<? extends LookAndFeel> laf;
		public final String lafPackage;
		public final String lafPrefix;

		public LAF(Class<? extends LookAndFeel> laf, String lafPrefix) {
			this.laf = laf;
			this.lafPackage = laf.getPackage().getName();
			this.lafPrefix = lafPrefix;
		}

		public String getDelegateName(String uiClassID) {
			return lafPrefix + uiClassID;
		}

		public String getQualifiedDelegateName(String uiClassID) {
			return lafPackage + "." + lafPrefix + uiClassID;
		}
	}
}
