/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.scenario.editor.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.Mode;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ExchangeItemAllocation;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.information.AbstractEventOperation;
import org.polarsys.capella.core.data.information.AbstractInstance;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.ScenarioKind;
import org.polarsys.capella.core.data.interaction.StateFragment;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.oa.Role;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ScenarioExt;
import org.polarsys.capella.core.sequencediag.ScenarioService;
import org.polarsys.capella.core.sirius.analysis.FaServices;
import org.polarsys.capella.core.sirius.analysis.InteractionServices;
import org.polarsys.capella.core.sirius.analysis.OAServices;
import org.polarsys.capella.scenario.editor.EmbeddedEditorInstance;

public class EmbeddedEditorInstanceHelper {

  public static final List<String> PARTICIPANT_KEYWORDS = Arrays.asList(DslConstants.ACTOR, DslConstants.ENTITY,
      DslConstants.ROLE, DslConstants.ACTIVITY, DslConstants.COMPONENT, DslConstants.CONFIGURATION_ITEM,
      DslConstants.FUNCTION);

  public static EList<InstanceRole> getAvailableInstanceRolesInDiagram() {
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return currentScenario.getOwnedInstanceRoles();
  }

  public static List<InstanceRole> getAvailableInstanceRoles() {
    List<String> participantKeywords = EmbeddedEditorInstanceHelper.PARTICIPANT_KEYWORDS;

    List<InstanceRole> instanceRoles = new ArrayList<InstanceRole>();
    for (String keyword : participantKeywords) {
      if (checkValidKeyword(keyword)) {
        List<InstanceRole> availableInstanceRoleForKeyword;
        if (keyword.equals(DslConstants.FUNCTION) || keyword.equals(DslConstants.ACTIVITY)) {
          availableInstanceRoleForKeyword = getAvailableElements(keyword).stream()
              .map(element -> ((AbstractFunction) element).getRepresentingInstanceRoles()).flatMap(Collection::stream)
              .distinct().collect(Collectors.toList());
        } else {
          availableInstanceRoleForKeyword = getAvailableElements(keyword).stream()
              .map(element -> ((AbstractInstance) element).getRepresentingInstanceRoles()).flatMap(Collection::stream)
              .distinct().collect(Collectors.toList());
        }
        instanceRoles.addAll(availableInstanceRoleForKeyword);
      }
    }
    return instanceRoles.stream().distinct().collect(Collectors.toList());
  }

  private static List<EObject> getAvailableAbstractInstances() {
    List<String> participantKeywords = EmbeddedEditorInstanceHelper.PARTICIPANT_KEYWORDS;

    List<EObject> instances = new ArrayList<>();
    for (String keyword : participantKeywords) {
      if (checkValidKeyword(keyword)) {
        instances.addAll(getAvailableElements(keyword));
      }
    }
    return instances.stream().distinct().collect(Collectors.toList());
  }

  public static String getScenarioType() {
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return currentScenario.getKind().toString();
  }

  public static boolean isInteractionScenario() {
    Scenario scenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return scenario.getKind().equals(ScenarioKind.INTERACTION);
  }

  public static boolean isInterfaceScenario() {
    Scenario scenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return scenario.getKind().equals(ScenarioKind.INTERFACE);
  }

  public static boolean isESScenario() {
    Scenario scenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return ((scenario.getKind() == ScenarioKind.INTERACTION) || (scenario.getKind() == ScenarioKind.DATA_FLOW));
  }

  public static boolean isCEScenario() {
    return ScenarioExt.isDataFlowBehaviouralScenario(EmbeddedEditorInstance.getAssociatedScenarioDiagram());
  }

  public static boolean isFEScenario() {
    return ScenarioExt.isDataFlowFunctionalScenario(EmbeddedEditorInstance.getAssociatedScenarioDiagram());
  }

  public static boolean isFSScenario() {
    return ScenarioExt.isFunctionalScenario(EmbeddedEditorInstance.getAssociatedScenarioDiagram());
  }

  public static EObject getScenarioLevel() {
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    return BlockArchitectureExt.getRootBlockArchitecture(currentScenario);
  }

  /**
   * get the names of the available exchanges
   * 
   * @param source
   *          - the name of the source element
   * @param target
   *          - the name of the target element
   * @return list of exchanges
   *
   */
  public static List<String> getExchangeNames(String source, String target) {
    List<String> messages = new ArrayList<String>();
    List<AbstractEventOperation> exchanges = getAvailableExchanges(source, target);
    for (AbstractEventOperation exchange : exchanges) {
      if (isInterfaceScenario()) {
        messages.add(((ExchangeItemAllocation) exchange).getAllocatedItem().getName());
      } else {
        messages.add(exchange.getName());
      }
    }

    return messages;
  }

  /**
   * get the names of the available exchanges
   * 
   * @param source
   *          - the name of the source element
   * @param target
   *          - the name of the target element
   * @return list of exchanges
   *
   */
  public static List<AbstractEventOperation> getExchangeMessages(String source, String target) {
    List<AbstractEventOperation> exchanges = getAvailableExchanges(source, target);
    return exchanges;
  }

  /**
   * returns the list of available exchanges possible to be inserted between source and target
   * 
   * @param source
   *          - the name of the source element
   * @param target
   *          - the name of the target element
   * @return list of exchanges
   *
   */
  public static List<AbstractEventOperation> getAvailableExchanges(String source, String target) {
    InstanceRole sourceIr = getInstanceRoleFromScenario(source);
    InstanceRole targetIr = getInstanceRoleFromScenario(target);
    if (sourceIr != null && targetIr != null) {
      return getAllAvailableExchanges(sourceIr.getRepresentedInstance(), targetIr.getRepresentedInstance());
    }

    List<AbstractEventOperation> availableExchanges = new ArrayList<AbstractEventOperation>();
    List<EObject> sourceCandidates = getAbstractInstanceCandidates(source);
    List<EObject> targetCandidates = getAbstractInstanceCandidates(target);

    for (EObject sourceObj : sourceCandidates) {
      for (EObject targetObj : targetCandidates) {
        AbstractInstance sourceInst = sourceObj instanceof AbstractInstance ? (AbstractInstance) sourceObj : null;
        AbstractInstance targetInst = targetObj instanceof AbstractInstance ? (AbstractInstance) targetObj : null;
        availableExchanges.addAll(getAllAvailableExchanges(sourceInst, targetInst));
      }
    }
    return availableExchanges;
  }

  /**
   * returns the list of available exchanges possible to be inserted between source and target
   * 
   * @param source
   *          - the source element
   * @param target
   *          - the target element
   * @return list of exchanges
   *
   */
  @SuppressWarnings("unchecked")
  private static List<AbstractEventOperation> getAllAvailableExchanges(AbstractInstance sourceInst,
      AbstractInstance targetInst) {
    List<AbstractEventOperation> exchangesAvailable = new ArrayList<AbstractEventOperation>();
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();

    switch (currentScenario.getKind()) {
    case FUNCTIONAL:
      if (sourceInst instanceof AbstractFunction && targetInst instanceof AbstractFunction) {
        exchangesAvailable.addAll(DataFlowHelper
            .getAvailableFonctionalExchangesFromFunctions((AbstractFunction) sourceInst, (AbstractFunction) targetInst)
            .stream().filter(x -> x instanceof AbstractEventOperation).collect(Collectors.toList()));
      }
      break;
    case INTERFACE:
      List<CapellaElement> exchanges = SelectInvokedOperationModelForSharedDataAndEvent
          .getAvailableExchangeItems(sourceInst, targetInst, false);
      for (CapellaElement exchange : exchanges) {
        if (exchange instanceof ExchangeItemAllocation) {
          exchangesAvailable.add((AbstractEventOperation) exchange);
        }
      }
      break;
    case DATA_FLOW:
    case INTERACTION:
      if (ScenarioExt.isFunctionalScenario(currentScenario)) {
        if (sourceInst instanceof AbstractFunction && targetInst instanceof AbstractFunction) {
          exchangesAvailable = DataFlowHelper
              .getAvailableFonctionalExchangesFromFunctions((AbstractFunction) sourceInst,
                  (AbstractFunction) targetInst)
              .stream().filter(x -> x instanceof AbstractEventOperation).collect(Collectors.toList());
        }
      } else {
        Component sourceComp = sourceInst != null && sourceInst.getAbstractType() instanceof Component
            ? (Component) sourceInst.getAbstractType()
            : null;
        Component targetComp = targetInst != null && targetInst.getAbstractType() instanceof Component
            ? (Component) targetInst.getAbstractType()
            : null;
        exchangesAvailable.addAll(
            (List<AbstractEventOperation>) DataFlowHelper.getAvailableComponentExchanges(sourceComp, targetComp));
        exchangesAvailable.addAll(DataFlowHelper.getAvailableFonctionalExchanges(sourceInst, targetInst).stream()
            .filter(x -> x instanceof AbstractEventOperation).collect(Collectors.toList()));

      }
      break;
    default:
      break;
    }

    return exchangesAvailable;
  }

  /**
   * returns the list of available elements as names that could be inserted
   * 
   * @param keyword
   * @return boolean
   *
   */
  public static List<String> getAvailablePartNames(String keyword) {
    List<String> partNames = new ArrayList<String>();

    Collection<? extends EObject> elements = getAvailableElements(keyword);
    for (EObject element : elements) {
      if (element instanceof AbstractNamedElement) {
        partNames.add(((AbstractNamedElement) element).getName());
      }
    }
    return partNames;
  }

  /**
   * returns the list of available abstract functions that could be inserted on the current scenario
   * 
   * @return collection of elements
   *
   */
  private static Collection<? extends EObject> getAvailableAbstractFunctions() {
    Collection<? extends EObject> elements = FaServices.getFaServices().getAllAbstractFunctions(
        BlockArchitectureExt.getRootBlockArchitecture(EmbeddedEditorInstance.getAssociatedScenarioDiagram()));
    return elements;
  }

  /**
   * returns the list of available components that could be inserted on the current scenario
   * 
   * @return collection of elements
   *
   */
  private static Collection<? extends EObject> getAvailableComponents() {
    Collection<? extends EObject> elements = (new InteractionServices())
        .getESScopeInsertComponents(EmbeddedEditorInstance.getAssociatedScenarioDiagram());
    return elements;
  }

  /**
   * returns the list of available actors that could be inserted on the current scenario
   * 
   * @return collection of elements
   *
   */
  private static Collection<? extends EObject> getAvailableActors() {
    Collection<? extends EObject> elements = (new InteractionServices())
        .getESScopeInsertActors(EmbeddedEditorInstance.getAssociatedScenarioDiagram());
    return elements;
  }

  /**
   * returns the list of available roles that could be inserted on the current scenario
   * 
   * @return collection of elements
   *
   */
  private static Collection<? extends EObject> getAvailableRoles() {
    Collection<? extends EObject> elements = OAServices.getService()
        .getOESScopeInsertEntitiesRoles(EmbeddedEditorInstance.getAssociatedScenarioDiagram()).stream()
        .filter(x -> x instanceof Role).collect(Collectors.toList());
    ;
    return elements;
  }

  /**
   * returns the list of available elements that could be inserted for the given keyword (which can be actor, component,
   * function, entity, role, activity, configuration_item)
   * 
   * @param keyword
   * @return boolean
   *
   */
  public static Collection<? extends EObject> getAvailableElements(String keyword) {
    switch (keyword) {
    case DslConstants.ACTIVITY:
    case DslConstants.FUNCTION:
      return getAvailableAbstractFunctions();
    case DslConstants.ROLE:
      return getAvailableRoles();
    case DslConstants.ACTOR:
      return getAvailableActors();
    default:
      // gets all the components (non actors)
      return getAvailableComponents();
    }
  }

  /**
   * returns instance role associated to the source (source is the name of the instance role)
   * 
   * @param soruce
   *          (name of the instance role)
   * @return InstanceRole
   *
   */
  public static InstanceRole getInstanceRole(String source) {
    List<InstanceRole> instanceRoles = getAvailableInstanceRolesInDiagram();
    InstanceRole instanceRole = null;
    for (Iterator<InstanceRole> iterator = instanceRoles.iterator(); iterator.hasNext();) {
      InstanceRole role = iterator.next();
      if (role.getName().equals(source)) {
        instanceRole = role;
        break;
      }
    }
    return instanceRole;
  }

  public static InstanceRole getInstanceRoleFromScenario(String source) {
    List<InstanceRole> instanceRoles = getAvailableInstanceRoles();
    InstanceRole instanceRole = null;
    for (Iterator<InstanceRole> iterator = instanceRoles.iterator(); iterator.hasNext();) {
      InstanceRole role = iterator.next();
      if (role.getName().equals(source)) {
        instanceRole = role;
        break;
      }
    }
    return instanceRole;
  }

  public static List<EObject> getAbstractInstanceCandidates(String name) {
    List<EObject> instances = getAvailableAbstractInstances().stream()
        .filter(inst -> (inst instanceof AbstractNamedElement && ((AbstractNamedElement) inst).getName().equals(name)))
        .collect(Collectors.toList());
    if (instances.isEmpty())
      instances.add(null);
    return instances;
  }

  /**
   * helper function that validates that a keyword typed in a text scenario is valid, based on scenario type and
   * architecture level
   * 
   * @param keyword
   * @return boolean
   *
   */
  public static boolean checkValidKeyword(String keyword) {
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    BlockArchitecture blockArchitecture = BlockArchitectureExt.getRootBlockArchitecture(currentScenario);

    if (currentScenario.getKind() == ScenarioKind.INTERACTION) {
      if (ScenarioExt.isFunctionalScenario(currentScenario)) {
        return keyword.equals(DslConstants.ACTIVITY);
      }
      return keyword.equals(DslConstants.ENTITY) || keyword.equals(DslConstants.ACTOR)
          || keyword.equals(DslConstants.ROLE);
    }
    // IS and ES
    if (currentScenario.getKind() == ScenarioKind.INTERFACE || currentScenario.getKind() == ScenarioKind.DATA_FLOW) {
      if (blockArchitecture instanceof SystemAnalysis || blockArchitecture instanceof LogicalArchitecture
          || blockArchitecture instanceof PhysicalArchitecture) {
        return keyword.equals(DslConstants.ACTOR) || keyword.equals(DslConstants.COMPONENT);
      }
      if (blockArchitecture instanceof EPBSArchitecture) {
        return keyword.equals(DslConstants.CONFIGURATION_ITEM);
      }
    }
    // FS Scenarios
    if (currentScenario.getKind() == ScenarioKind.FUNCTIONAL) {
      if (blockArchitecture instanceof SystemAnalysis || blockArchitecture instanceof LogicalArchitecture
          || blockArchitecture instanceof PhysicalArchitecture) {
        return keyword.equals(DslConstants.FUNCTION);
      }
    }
    return false;
  }

  public static boolean checkValidTimeline(String timelineName) {
    if (timelineName == null || getInstanceRoleFromScenario(timelineName) == null)
      return false;
    return true;
  }

  /**
   * Get all available state fragments (mode, state or allocated function) for a specific instance role
   * 
   * @param type
   *          - the type of state fragment: mode, state or allocated function
   * @param timelineName
   *          - the timeline name
   * @return List<String> - return the list with all available state fragments
   */
  public static List<String> getAvailableStateFragments(String type, String timelineName) {
    if (timelineName == null || type == null) {
      return new ArrayList<String>();
    }

    EObject element = getInstanceRoleFromScenario(timelineName);
    if (element == null)
      return new ArrayList<String>();

    if (type.equals(DslConstants.FUNCTION))
      return getAllocatedFunctionsName(element);

    Collection<AbstractState> modesAndStates = ScenarioExt
        .getAvailableStateModeStateFragment(((InstanceRole) element).getRepresentedInstance());
    if (modesAndStates.isEmpty())
      return new ArrayList<String>();

    if (type.equals(DslConstants.MODE))
      return getModesNames(modesAndStates);
    return getStatesNames(modesAndStates);
  }

  private static List<String> getModesNames(Collection<AbstractState> modesAndStates) {
    List<String> modesNames = modesAndStates.stream().filter(x -> x instanceof Mode).map(x -> x.getName())
        .collect(Collectors.toList());
    return modesNames;
  }

  private static List<String> getStatesNames(Collection<AbstractState> modesAndStates) {
    List<String> statesNames = modesAndStates.stream().filter(x -> x instanceof State && !(x instanceof Mode))
        .map(x -> x.getName()).collect(Collectors.toList());
    return statesNames;
  }

  private static List<String> getAllocatedFunctionsName(EObject element) {
    Collection<AbstractFunction> allocFunctions = ScenarioExt
        .getAvailableFunctionsStateFragment(((InstanceRole) element).getRepresentedInstance());

    if (!allocFunctions.isEmpty()) {
      return allocFunctions.stream().map(x -> x.getName()).collect(Collectors.toList());
    }
    return new ArrayList<String>();
  }

  public static void updateModel(String content) {
    XtextDocument document = EmbeddedEditorInstance.getEmbeddedEditor().getDocument();
    document.set(content);
    document.readOnly(new IUnitOfWork.Void<XtextResource>() {
      @Override
      public void process(XtextResource state) throws Exception {
      }
    });
  }

  public static String getModelContent() {
    XtextDocument document = EmbeddedEditorInstance.getEmbeddedEditor().getDocument();
    return document.get();
  }

  public static List<AbstractState> getStates(InstanceRole element) {
    Collection<AbstractState> modesAndStates = ScenarioExt
        .getAvailableStateModeStateFragment(element.getRepresentedInstance());
    return modesAndStates.stream().filter(x -> x instanceof State && !(x instanceof Mode)).collect(Collectors.toList());
  }

  public static List<AbstractState> getModes(InstanceRole element) {
    Collection<AbstractState> modesAndStates = ScenarioExt
        .getAvailableStateModeStateFragment(element.getRepresentedInstance());
    return modesAndStates.stream().filter(x -> x instanceof Mode).collect(Collectors.toList());
  }

  public static List<AbstractFunction> getAllocatedFunctions(InstanceRole element) {
    return ScenarioExt.getAvailableFunctionsStateFragment(element.getRepresentedInstance()).stream()
        .collect(Collectors.toList());
  }

  public static String getStateFragmentType(StateFragment capellaStateFragment) {
    if (capellaStateFragment.getRelatedAbstractFunction() != null) {
      return DslConstants.FUNCTION;
    }
    if (capellaStateFragment.getRelatedAbstractState() != null
        && capellaStateFragment.getRelatedAbstractState() instanceof Mode) {
      return DslConstants.MODE;
    }
    return DslConstants.STATE;
  }

  public static String getStateFragmentName(StateFragment capellaStateFragment) {
    if (capellaStateFragment.getRelatedAbstractFunction() != null) {
      return capellaStateFragment.getRelatedAbstractFunction().getName();
    } else if (capellaStateFragment.getRelatedAbstractState() != null) {
      return capellaStateFragment.getRelatedAbstractState().getName();
    }
    return null;
  }

  /*
   * get the list of opened and diagrams
   */
  private static List<DDiagram> getOpenedRepresentations() {
    List<DDiagram> diagrams = new ArrayList<DDiagram>();
    IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    if (activePage == null) {
      return null;
    }
    for (IEditorReference ref : activePage.getEditorReferences()) {
      IEditorPart editor = ref.getEditor(false);
      if (editor != null && editor instanceof DDiagramEditor) {
        DDiagramEditor dEditor = (DDiagramEditor) editor;
        DRepresentation representation = dEditor.getRepresentation();
        if (representation instanceof DDiagram) {
          diagrams.add((DDiagram) representation);
        }
      }
    }
    return diagrams;
  }

  /*
   * get the first opened sequence diagram
   */
  public static DDiagram getFirstOpenedRepresentation() {
    List<DDiagram> diagrams = getOpenedRepresentations();
    if (!diagrams.isEmpty()) {
      return diagrams.get(0);
    }
    return null;
  }

  /*
   * In case we have a sequence diagram, set the initial diagram to this opened one
   */
  public static void setOpenedRepresentation() {
    DDiagram diagram = getFirstOpenedRepresentation();
    if (diagram instanceof SequenceDDiagram) {
      EmbeddedEditorInstance.setDDiagram(diagram);
    }
  }

  /*
   * check if the associated diagram of the text editor is opened
   */
  public static boolean isOpenedRepresentation() {
    List<DDiagram> diagrams = getOpenedRepresentations();
    return diagrams.contains(EmbeddedEditorInstance.getDDiagram());
  }

  public static void refreshAssociatedDiagram() {
    DDiagram diagram = EmbeddedEditorInstance.getDDiagram();
    if (diagram != null) {
      DialectManager.INSTANCE.refresh(diagram, new NullProgressMonitor());
    }
  }

  /*
   * Get all possible scenario references
   */
  public static List<String> getReferencedScenariosName() {
    ScenarioService scenarioService = new ScenarioService();
    Scenario currentScenario = EmbeddedEditorInstance.getAssociatedScenarioDiagram();
    List<EObject> referenceScope = scenarioService.getReferenceScope(currentScenario);
    return referenceScope.stream().map(x -> ((Scenario) x).getName()).collect(Collectors.toList());
  }
}
