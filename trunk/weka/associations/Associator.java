/*
 *    Associator.java
 *    Copyright (C) 1999 Eibe Frank
 *
 */

package weka.associations;

import java.io.*;
import weka.core.*;

/** 
 * Abstract scheme for learning associations. All schemes for learning
 * associations implemement this class
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version $Revision: 1.2 $ 
 */
public abstract class Associator implements Cloneable, Serializable {
 
  /**
   * Generates an associator. Must initialize all fields of the associator
   * that are not being set via options (ie. multiple calls of buildAssociator
   * must always lead to the same result). Must not change the dataset
   * in any way.
   *
   * @param data set of instances serving as training data 
   * @exception Exception if the associator has not been 
   * generated successfully
   */
  public abstract void buildAssociations(Instances data) throws Exception;

  /**
   * Creates a new instance of a associator given it's class name and
   * (optional) arguments to pass to it's setOptions method. If the
   * associator implements OptionHandler and the options parameter is
   * non-null, the associator will have it's options set.
   *
   * @param associatorName the fully qualified class name of the associator
   * @param options an array of options suitable for passing to setOptions. May
   * be null.
   * @return the newly created associator, ready for use.
   * @exception Exception if the associator name is invalid, or the options
   * supplied are not acceptable to the associator
   */
  public static Associator forName(String associatorName,
				   String [] options) throws Exception {

    return (Associator)Utils.forName(Associator.class,
				     associatorName,
				     options);
  }

  /**
   * Creates copies of the current associator.
   *
   * @param model an example associator to copy
   * @param num the number of associators copies to create.
   * @return an array of associators.
   * @exception Exception if an error occurs
   */
  public static Associator [] makeCopies(Associator model,
					 int num) throws Exception {

    if (model == null) {
      throw new Exception("No model associator set");
    }
    Associator [] associators = new Associator [num];
    String [] options = null;
    if (model instanceof OptionHandler) {
      options = ((OptionHandler)model).getOptions();
    }
    for(int i = 0; i < associators.length; i++) {
      associators[i] = (Associator) model.getClass().newInstance();
      if (options != null) {
	String [] tempOptions = (String [])options.clone();
	((OptionHandler)associators[i]).setOptions(tempOptions);
	Utils.checkForRemainingOptions(tempOptions);
      }
    }
    return associators;
  }
}

