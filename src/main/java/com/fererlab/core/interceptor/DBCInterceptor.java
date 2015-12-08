package com.fererlab.core.interceptor;

import com.fererlab.core.annotation.Contracted;
import com.fererlab.core.annotation.Ensures;
import com.fererlab.core.annotation.Invariant;
import com.fererlab.core.annotation.Requires;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class DBCInterceptor {

    private ScriptEngine scriptEngine = null;

    @AroundInvoke
    public Object executeContracts(InvocationContext ctx) throws Exception {
        // scripting engine reference
        // check if the class is designed according to Design By Contract
        Contracted contracted = ctx.getTarget().getClass().getAnnotation(Contracted.class);
        // if the contract annotation available then execute invariant, requires and ensures expressions
        if (contracted != null) {
            // clean the engine
            Bindings bindings = getScriptEngine().getBindings(ScriptContext.ENGINE_SCOPE);
            bindings.clear();

            // set bean and params
            getScriptEngine().getContext().setAttribute("bean", ctx.getTarget(), ScriptContext.ENGINE_SCOPE);
            getScriptEngine().getContext().setAttribute("params", ctx.getParameters(), ScriptContext.ENGINE_SCOPE);
            // check if this method has any requirements
            Requires requires = ctx.getMethod().getDeclaredAnnotation(Requires.class);
            if (requires != null) {
                String[] expressions = requires.value();
                // execute expression
                for (String expression : expressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Requires Expression", expression);
                    }
                }
            }
        }

        // proceed to method
        Object result = ctx.proceed();

        // if the contract annotation available then execute invariant, requires and ensures expressions
        if (contracted != null) {
            // check if this method has any requirements
            Ensures ensures = ctx.getMethod().getDeclaredAnnotation(Ensures.class);
            if (ensures != null) {
                String[] ensureExpressions = ensures.value();
                // set result
                getScriptEngine().getContext().setAttribute("result", result, ScriptContext.ENGINE_SCOPE);
                // check if this method has any ensures
                for (String expression : ensureExpressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Ensures Expression", expression);
                    }
                }
            }

            // if there is an invariant it should be true
            Invariant invariant = ctx.getTarget().getClass().getAnnotation(Invariant.class);
            if (invariant != null) {
                String[] expressions = invariant.value();
                // check if this method has any invariants
                for (String expression : expressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Invariant Expression", expression);
                    }
                }
            }
        }

        // return method result
        return result;
    }

    private ScriptEngine getScriptEngine() {
        // scripting engine for expression evaluation
        if (scriptEngine == null) {
            scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        }
        return scriptEngine;
    }
}
