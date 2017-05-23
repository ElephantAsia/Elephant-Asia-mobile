package fr.elephantasia.activities.addElephant.fragment;


import android.support.v4.app.Fragment;

public class ParentageFragment extends Fragment {

//  private View fatherParent;
//  private View motherParent;
//
//  private View fatherOverview;
//  private View motherOverview;
//
//  private View fatherAddButton;
//  private View motherAddButton;
//
//  private ViewGroup childrenContainer;
//  private View childrenAddButton;
//
//  private ElephantInfo father;
//  private ElephantInfo mother;
//  private List<ElephantInfo> children;
//
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    children = new ArrayList<>();
//  }
//
//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    View view = inflater.inflate(R.layout.add_elephant_parentage_fragment, container, false);
//
//    fatherParent = view.findViewById(R.id.father_parent);
//    motherParent = view.findViewById(R.id.mother_parent);
//
//    fatherOverview = view.findViewById(R.id.father_overview);
//    motherOverview = view.findViewById(R.id.mother_overview);
//
//    fatherAddButton = view.findViewById(R.id.father_add_button);
//    motherAddButton = view.findViewById(R.id.mother_add_button);
//    childrenAddButton = view.findViewById(R.id.children_add_button);
//
//    childrenContainer = (ViewGroup) view.findViewById(R.id.children_list);
//
//    if (father == null) {
//      fatherOverview.setVisibility(View.GONE);
//      fatherAddButton.setVisibility(View.VISIBLE);
//    } else {
//      fatherOverview.setVisibility(View.VISIBLE);
//      fatherAddButton.setVisibility(View.GONE);
//      refreshFather(father);
//    }
//
//    if (mother == null) {
//      motherOverview.setVisibility(View.GONE);
//      motherAddButton.setVisibility(View.VISIBLE);
//    } else {
//      motherOverview.setVisibility(View.VISIBLE);
//      motherAddButton.setVisibility(View.GONE);
//      refreshMother(mother);
//    }
//
//    fatherAddButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        ((AddElephantInterface) getActivity()).setFather();
//      }
//    });
//
//    motherAddButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        ((AddElephantInterface) getActivity()).setMother();
//      }
//    });
//
//    childrenAddButton.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        ((AddElephantInterface) getActivity()).addChildren();
//      }
//    });
//
//    fatherOverview.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        if (father != null) {
//          ((AddElephantInterface) getActivity()).onElephantClick(father);
//        }
//      }
//    });
//
//    motherOverview.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        if (mother != null) {
//          ((AddElephantInterface) getActivity()).onElephantClick(mother);
//        }
//      }
//    });
//
//    refreshChildrenList();
//
//    return (view);
//  }
//
//  public void refreshFather(ElephantInfo info) {
//    Log.i("father", info.name);
//    father = info;
//    fatherAddButton.setVisibility(View.GONE);
//    fatherOverview.setVisibility(View.VISIBLE);
//    RefreshElephantPreview.refresh(getContext(), fatherOverview, info);
//  }
//
//  public void refreshMother(ElephantInfo info) {
//    Log.i("mother", info.name);
//    mother = info;
//    motherAddButton.setVisibility(View.GONE);
//    motherOverview.setVisibility(View.VISIBLE);
//    RefreshElephantPreview.refresh(getContext(), motherOverview, info);
//  }
//
//  public void refreshChildren(final ElephantInfo info) {
//    Log.i("children", "add: " + info.name);
//    children.add(info);
//
//    LayoutInflater inflater = LayoutInflater.from(getActivity());
//    View view = inflater.inflate(R.layout.elephant_preview, childrenContainer, false);
//
//    RefreshElephantPreview.refresh(getContext(), view, info);
//
//    view.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        ((AddElephantInterface) getActivity()).onElephantClick(info);
//      }
//    });
//
//    childrenContainer.addView(view);
//  }
//
//  private void refreshChildrenList() {
//    childrenContainer.removeAllViews();
//
//    LayoutInflater inflater = LayoutInflater.from(getActivity());
//    for (final ElephantInfo info : children) {
//      View view = inflater.inflate(R.layout.elephant_preview, childrenContainer, false);
//
//      RefreshElephantPreview.refresh(getContext(), view, info);
//
//      view.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//          ((AddElephantInterface) getActivity()).onElephantClick(info);
//        }
//      });
//
//      childrenContainer.addView(view);
//    }
//  }

}