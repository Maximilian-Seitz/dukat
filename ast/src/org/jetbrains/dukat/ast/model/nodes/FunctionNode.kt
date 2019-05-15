package org.jetbrains.dukat.ast.model.nodes

import org.jetbrains.dukat.astCommon.MemberEntity
import org.jetbrains.dukat.astCommon.TopLevelEntity
import org.jetbrains.dukat.tsmodel.types.ParameterValueDeclaration

data class FunctionNode(
        val name: NameNode,
        val parameters: List<ParameterNode>,
        val type: ParameterValueDeclaration,
        val typeParameters: List<TypeValueNode>,

        val generatedReferenceNodes: MutableList<GeneratedInterfaceReferenceNode>,
        val annotations: MutableList<AnnotationNode>,

        val export: Boolean,
        val inline: Boolean,
        val operator: Boolean,

        var owner: DocumentRootNode?,
        val body: List<StatementNode>,
        val uid: String
) : MemberEntity, TopLevelEntity, MergableNode, MemberNode