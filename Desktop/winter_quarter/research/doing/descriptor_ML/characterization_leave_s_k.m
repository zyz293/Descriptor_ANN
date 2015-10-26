%function matreader()
data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\characterization_new.csv',1,1);
fid = fopen('characterization_leave_s_k.csv','w');

fprintf(fid,'%s,','sample_name');
fprintf(fid,'%s,','R_vfs');
fprintf(fid,'%s,','R_n');
fprintf(fid,'%s,','R_intph0');
fprintf(fid,'%s,','R_intph1');
fprintf(fid,'%s,','R_area_m');
fprintf(fid,'%s,','R_area_v');
fprintf(fid,'%s,','R_comp_m');
fprintf(fid,'%s,','R_comp_v');
fprintf(fid,'%s,','R_eccen_m');
fprintf(fid,'%s,','R_eccen_v');
fprintf(fid,'%s,','R_els_m');
fprintf(fid,'%s,','R_els_v');
fprintf(fid,'%s,','P_locvf_m');
fprintf(fid,'%s,','P_locvf_v');
fprintf(fid,'%s,','P_nbd_m');
fprintf(fid,'%s,','P_nbd_v');
fprintf(fid,'%s,','P_ncd_m');
fprintf(fid,'%s,','P_ncd_v');
fprintf(fid,'%s,','P_ornang_m');
fprintf(fid,'%s,','P_ornang_v');
fprintf(fid,'%s,','P_pores_m');
fprintf(fid,'%s,','P_pores_v');
fprintf(fid,'%s,','P_rc_m');
fprintf(fid,'%s,','P_rc_v');
fprintf(fid,'%s,','P_rctan_m');
fprintf(fid,'%s,','P_rctan_v');
fprintf(fid,'%s,','P_rnds_m');
fprintf(fid,'%s,','P_rnds_v');
fprintf(fid,'%s,','P_ttst_m');
fprintf(fid,'%s\n','P_ttst_v');

sample_name = fopen('C:\Users\DaTui\Desktop\winter_quarter\research\characterization data\sample_name_file\sample_name.csv');
name = textscan(sample_name, '%s %*[^\n]');
for i = 1:193
   samplename = strsplit(name{1,1}{i,1}, '.');
   name{1,1}{i,1} = samplename{1};
end

for i = 1:193
    fprintf(fid, '%s,', name{1,1}{i,1});
    fprintf(fid, '%d,', data(i,1));
    fprintf(fid, '%d,', data(i,2));
    fprintf(fid, '%d,', data(i,3));
    fprintf(fid, '%d,', data(i,4));
    for j = 1:12
        fprintf(fid, '%d,', data(i,4*j+1));
        fprintf(fid, '%d,', data(i,4*j+2));
    end
    fprintf(fid, '%d,', data(i,53));
    fprintf(fid, '%d\n', data(i,54));
end

fclose(fid);

%end