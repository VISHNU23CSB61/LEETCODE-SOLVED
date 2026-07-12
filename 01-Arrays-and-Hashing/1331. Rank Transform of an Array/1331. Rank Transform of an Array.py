class Solution {
public:
    vector<int> arrayRankTransform(vector<int>& arr) {
        if(arr.size() == 0) return {};
        
        vector<pair<int,int>> vp;
        for(int i=0; i<arr.size(); i++) vp.push_back({arr[i], i});
        sort(begin(vp), end(vp));

        vector<int> ans(arr.size());
        ans[vp[0].second] = 1;

        for(int i=1; i<vp.size(); i++){
            if(vp[i].first == vp[i-1].first){
                ans[vp[i].second] = ans[vp[i-1].second];
            }    
            else{
                ans[vp[i].second] = ans[vp[i-1].second] + 1;
            }
        }

        return ans;
    }
};