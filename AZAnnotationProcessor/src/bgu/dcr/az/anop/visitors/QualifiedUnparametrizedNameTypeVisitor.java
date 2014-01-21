/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.dcr.az.anop.visitors;

import javax.annotation.processing.Messager;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor7;
import javax.tools.Diagnostic;

/**
 *
 * @author User
 */
public class QualifiedUnparametrizedNameTypeVisitor extends SimpleTypeVisitor7<String, Void> {

    private Messager msg;

    public QualifiedUnparametrizedNameTypeVisitor(Messager msg) {
        this.msg = msg;
    }

    @Override
    public String visitDeclared(DeclaredType t, Void p) {
        return t.asElement().toString();
    }

    @Override
    protected String defaultAction(TypeMirror e, Void p) {
        error("Unknown type decleration: " + e);
        return null;
    }

    public void error(String err) {
        msg.printMessage(Diagnostic.Kind.ERROR, err);
    }

}
