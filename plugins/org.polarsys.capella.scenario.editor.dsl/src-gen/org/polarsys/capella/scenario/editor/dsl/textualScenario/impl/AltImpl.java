/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0
 *  
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *     Thales - initial API and implementation
 ******************************************************************************/
/**
 * generated by Xtext 2.18.0.M3
 */
package org.polarsys.capella.scenario.editor.dsl.textualScenario.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.polarsys.capella.scenario.editor.dsl.textualScenario.Alt;
import org.polarsys.capella.scenario.editor.dsl.textualScenario.Block;
import org.polarsys.capella.scenario.editor.dsl.textualScenario.ElseBlock;
import org.polarsys.capella.scenario.editor.dsl.textualScenario.TextualScenarioPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alt</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.polarsys.capella.scenario.editor.dsl.textualScenario.impl.AltImpl#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link org.polarsys.capella.scenario.editor.dsl.textualScenario.impl.AltImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.polarsys.capella.scenario.editor.dsl.textualScenario.impl.AltImpl#getTimelines <em>Timelines</em>}</li>
 *   <li>{@link org.polarsys.capella.scenario.editor.dsl.textualScenario.impl.AltImpl#getBlock <em>Block</em>}</li>
 *   <li>{@link org.polarsys.capella.scenario.editor.dsl.textualScenario.impl.AltImpl#getElseBlocks <em>Else Blocks</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AltImpl extends CombinedFragmentImpl implements Alt
{
  /**
   * The default value of the '{@link #getKeyword() <em>Keyword</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKeyword()
   * @generated
   * @ordered
   */
  protected static final String KEYWORD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getKeyword() <em>Keyword</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getKeyword()
   * @generated
   * @ordered
   */
  protected String keyword = KEYWORD_EDEFAULT;

  /**
   * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected static final String CONDITION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getCondition() <em>Condition</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCondition()
   * @generated
   * @ordered
   */
  protected String condition = CONDITION_EDEFAULT;

  /**
   * The cached value of the '{@link #getTimelines() <em>Timelines</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimelines()
   * @generated
   * @ordered
   */
  protected EList<String> timelines;

  /**
   * The cached value of the '{@link #getBlock() <em>Block</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBlock()
   * @generated
   * @ordered
   */
  protected Block block;

  /**
   * The cached value of the '{@link #getElseBlocks() <em>Else Blocks</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElseBlocks()
   * @generated
   * @ordered
   */
  protected EList<ElseBlock> elseBlocks;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AltImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return TextualScenarioPackage.Literals.ALT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getKeyword()
  {
    return keyword;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setKeyword(String newKeyword)
  {
    String oldKeyword = keyword;
    keyword = newKeyword;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TextualScenarioPackage.ALT__KEYWORD, oldKeyword, keyword));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getCondition()
  {
    return condition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setCondition(String newCondition)
  {
    String oldCondition = condition;
    condition = newCondition;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TextualScenarioPackage.ALT__CONDITION, oldCondition, condition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getTimelines()
  {
    if (timelines == null)
    {
      timelines = new EDataTypeEList<String>(String.class, this, TextualScenarioPackage.ALT__TIMELINES);
    }
    return timelines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Block getBlock()
  {
    return block;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBlock(Block newBlock, NotificationChain msgs)
  {
    Block oldBlock = block;
    block = newBlock;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TextualScenarioPackage.ALT__BLOCK, oldBlock, newBlock);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBlock(Block newBlock)
  {
    if (newBlock != block)
    {
      NotificationChain msgs = null;
      if (block != null)
        msgs = ((InternalEObject)block).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TextualScenarioPackage.ALT__BLOCK, null, msgs);
      if (newBlock != null)
        msgs = ((InternalEObject)newBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TextualScenarioPackage.ALT__BLOCK, null, msgs);
      msgs = basicSetBlock(newBlock, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TextualScenarioPackage.ALT__BLOCK, newBlock, newBlock));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<ElseBlock> getElseBlocks()
  {
    if (elseBlocks == null)
    {
      elseBlocks = new EObjectContainmentEList<ElseBlock>(ElseBlock.class, this, TextualScenarioPackage.ALT__ELSE_BLOCKS);
    }
    return elseBlocks;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case TextualScenarioPackage.ALT__BLOCK:
        return basicSetBlock(null, msgs);
      case TextualScenarioPackage.ALT__ELSE_BLOCKS:
        return ((InternalEList<?>)getElseBlocks()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case TextualScenarioPackage.ALT__KEYWORD:
        return getKeyword();
      case TextualScenarioPackage.ALT__CONDITION:
        return getCondition();
      case TextualScenarioPackage.ALT__TIMELINES:
        return getTimelines();
      case TextualScenarioPackage.ALT__BLOCK:
        return getBlock();
      case TextualScenarioPackage.ALT__ELSE_BLOCKS:
        return getElseBlocks();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case TextualScenarioPackage.ALT__KEYWORD:
        setKeyword((String)newValue);
        return;
      case TextualScenarioPackage.ALT__CONDITION:
        setCondition((String)newValue);
        return;
      case TextualScenarioPackage.ALT__TIMELINES:
        getTimelines().clear();
        getTimelines().addAll((Collection<? extends String>)newValue);
        return;
      case TextualScenarioPackage.ALT__BLOCK:
        setBlock((Block)newValue);
        return;
      case TextualScenarioPackage.ALT__ELSE_BLOCKS:
        getElseBlocks().clear();
        getElseBlocks().addAll((Collection<? extends ElseBlock>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case TextualScenarioPackage.ALT__KEYWORD:
        setKeyword(KEYWORD_EDEFAULT);
        return;
      case TextualScenarioPackage.ALT__CONDITION:
        setCondition(CONDITION_EDEFAULT);
        return;
      case TextualScenarioPackage.ALT__TIMELINES:
        getTimelines().clear();
        return;
      case TextualScenarioPackage.ALT__BLOCK:
        setBlock((Block)null);
        return;
      case TextualScenarioPackage.ALT__ELSE_BLOCKS:
        getElseBlocks().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case TextualScenarioPackage.ALT__KEYWORD:
        return KEYWORD_EDEFAULT == null ? keyword != null : !KEYWORD_EDEFAULT.equals(keyword);
      case TextualScenarioPackage.ALT__CONDITION:
        return CONDITION_EDEFAULT == null ? condition != null : !CONDITION_EDEFAULT.equals(condition);
      case TextualScenarioPackage.ALT__TIMELINES:
        return timelines != null && !timelines.isEmpty();
      case TextualScenarioPackage.ALT__BLOCK:
        return block != null;
      case TextualScenarioPackage.ALT__ELSE_BLOCKS:
        return elseBlocks != null && !elseBlocks.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (keyword: ");
    result.append(keyword);
    result.append(", condition: ");
    result.append(condition);
    result.append(", timelines: ");
    result.append(timelines);
    result.append(')');
    return result.toString();
  }

} //AltImpl
