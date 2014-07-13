package fr.egiov.concoursfleches.tapestry.composants;

import java.lang.reflect.Modifier;

import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.util.BodyBuilder;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.TransformMethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ajoute à une classe :
 * <ul>
 * <li>un attribut private PropertyAccess utilisé par le model et l'encoder</li>
 * <li>les méthodes getSelectionModel() et getValueEncoder() utilisées par le composant</li>
 * </ul>
 * @author giovarej
 */
public class InjectSelectionModelWorker implements ComponentClassTransformWorker
{
   // ------------------------- Membres private -------------------------

   /** le logger */
   final private static Logger s_Logger = LoggerFactory.getLogger(InjectSelectionModelWorker.class);

   /** service permettant d'accéder à une propriété d'une classe */
   final private PropertyAccess m_PropertyAccess;

   // ------------------------- Constructeur -------------------------

   /**
    * Constructeur
    * @param p_PropertyAccess le service permettant d'accéder à une propriété d'une classe
    */
   public InjectSelectionModelWorker(PropertyAccess p_PropertyAccess)
   {
      m_PropertyAccess = p_PropertyAccess;
   }

   // ------------------------- Méthodes public -------------------------
   /**
    * {@inheritDoc}
    * @see org.apache.tapestry5.services.ComponentClassTransformWorker#transform(org.apache.tapestry5.services.ClassTransformation,
    *      org.apache.tapestry5.model.MutableComponentModel)
    */
   @Override
   public void transform(ClassTransformation p_Transformation,
                         MutableComponentModel p_ComponentModel)
   {
      for (String fieldName : p_Transformation.findFieldsWithAnnotation(InjectSelectionModel.class))
      {
         InjectSelectionModel annotation =
            p_Transformation.getFieldAnnotation(fieldName, InjectSelectionModel.class);
         
         if (s_Logger.isDebugEnabled())
         {
            s_Logger.debug("creation du getter du Selection model pour le champ " + fieldName);
         }
         String accessActualName =
            p_Transformation.addField(Modifier.PRIVATE, PropertyAccess.class.getName(),
               "m_PropertyAccess");
         p_Transformation.injectField(accessActualName, m_PropertyAccess);

         addGetSelectionModelMethod(p_Transformation, fieldName, annotation.labelField(),
            accessActualName);

         if (s_Logger.isDebugEnabled())
         {
            s_Logger.debug("creation du getter du value encoder pour le champ " + fieldName);
         }

         addGetValueEncoderMethod(p_Transformation, fieldName, annotation.idField(),
            accessActualName);

      }

   }

   // ------------------------- Méthodes private -------------------------

   /**
    * Ajoute la méthode getSelectionMsodel() correspondant à un SELECT à la classe
    * @param p_Transformation la classe à modifier
    * @param p_FieldName l'attribut représentant le select
    * @param p_LabelField le label du champ
    * @param p_PropertyAccessName le nom du service PropertyAccess
    */
   private void addGetSelectionModelMethod(ClassTransformation p_Transformation,
                                           String p_FieldName,
                                           String p_LabelField,
                                           String p_PropertyAccessName)
   {

      String methodName =
         "get" + InternalUtils.capitalize(InternalUtils.stripMemberName(p_FieldName))
            + "SelectionModel";

      String modelQualifiedName = (GenericSelectionModel.class).getName();
      TransformMethodSignature sig =
         new TransformMethodSignature(Modifier.PUBLIC, modelQualifiedName, methodName, null, null);

      BodyBuilder builder = new BodyBuilder();
      builder.begin();
      builder.addln("return new " + modelQualifiedName + "(" + p_FieldName + ", \"" + p_LabelField
         + "\", " + p_PropertyAccessName + ");");
      builder.end();

      p_Transformation.addMethod(sig, builder.toString());

   }

   /**
    * Ajoute la méthode getValueEncoder() correspondant à un SELECT à la classe
    * @param p_Transformation la classe à modifier
    * @param p_FieldName l'attribut représentant le select
    * @param p_LabelField le label du champ
    * @param p_PropertyAccessName le nom du service PropertyAccess
    */
   private void addGetValueEncoderMethod(ClassTransformation p_Transformation,
                                         String p_FieldName,
                                         String p_LabelField,
                                         String p_PropertyAccessName)
   {

      String methodName =
         "get" + InternalUtils.capitalize(InternalUtils.stripMemberName(p_FieldName))
            + "ValueEncoder";

      String encoderQualifiedName = (GenericValueEncoder.class).getName();
      TransformMethodSignature sig =
         new TransformMethodSignature(Modifier.PUBLIC, encoderQualifiedName, methodName, null, null);

      BodyBuilder builder = new BodyBuilder();
      builder.begin();
      String line =
         "return new " + encoderQualifiedName + "(" + p_FieldName + ",\"" + p_LabelField + "\", "
            + p_PropertyAccessName + ");";
      builder.addln(line);
      builder.end();

      p_Transformation.addMethod(sig, builder.toString());
   }
}
