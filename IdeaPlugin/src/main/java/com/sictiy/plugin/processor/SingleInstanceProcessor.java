package com.sictiy.plugin.processor;

/**
 * @author sictiy.xu
 * @version 2019/10/14 15:31
 **/
public class SingleInstanceProcessor extends AbstracProcessor
{
    //    private static final String METHOD_NAME = "getInstance";
    //
    //    protected SingleInstanceProcessor(@NotNull Class<? extends PsiElement> supportedClass, @NotNull Class<? extends Annotation> supportedAnnotationClass)
    //    {
    //        super(PsiClass.class, SingleInstance.class);
    //    }
    //
    //    @Override
    //    protected boolean validate(@NotNull PsiAnnotation psiAnnotation, @NotNull PsiClass psiClass, @NotNull ProblemBuilder builder)
    //    {
    //        if (!validateAnnotationOnRigthType(psiClass, builder))
    //        {
    //            return false;
    //        }
    //        if (!validateExistingMethods(psiClass, builder))
    //        {
    //            return false;
    //        }
    //        return true;
    //    }
    //
    //    private boolean validateAnnotationOnRigthType(@NotNull PsiClass psiClass, @NotNull ProblemBuilder builder)
    //    {
    //        boolean result = true;
    //        if (psiClass.isAnnotationType() || psiClass.isInterface())
    //        {
    //            builder.addError("@SingleInstance is only supported on a class or enum type");
    //            result = false;
    //        }
    //        return result;
    //    }
    //
    //    private boolean validateExistingMethods(@NotNull PsiClass psiClass, @NotNull ProblemBuilder builder)
    //    {
    //        boolean result = true;
    //
    //        final Collection<PsiMethod> classMethods = PsiClassUtil.collectClassMethodsIntern(psiClass);
    //        if (PsiMethodUtil.hasMethodByName(classMethods, METHOD_NAME))
    //        {
    //            builder.addWarning("Not generated '%s'(): A method with same name already exists", METHOD_NAME);
    //            result = false;
    //        }
    //
    //        return result;
    //    }
    //
    //    @Override
    //    protected void generatePsiElements(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation, @NotNull List<? super PsiElement> target)
    //    {
    //        target.addAll(createGetInstanceMethod(psiClass, psiAnnotation));
    //    }
    //
    //    private Collection<PsiMethod> createGetInstanceMethod(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation)
    //    {
    //        final PsiMethod method = createOneGetInstanceMethod(psiClass, psiAnnotation);
    //        return Collections.singletonList(method);
    //    }
    //
    //    private PsiMethod createOneGetInstanceMethod(@NotNull PsiClass psiClass, @NotNull PsiAnnotation psiAnnotation)
    //    {
    //        final PsiManager psiManager = psiClass.getManager();
    //
    //        final LombokLightMethodBuilder methodBuilder = new LombokLightMethodBuilder(psiManager, METHOD_NAME);
    //        methodBuilder.withMethodReturnType(PsiClassUtil.getTypeWithGenerics(psiClass));
    //        methodBuilder.withModifier(PsiModifier.PUBLIC);
    //        methodBuilder.withModifier(PsiModifier.STATIC);
    //        return methodBuilder;
    //    }
}
