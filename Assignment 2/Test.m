function [] = Test()

a = [5,2,3,4,2];
disp(mode(a));

% originalDataset = load('characters4.mat');
% disp(originalDataset);
% dataset = originalDataset.Chars1Stroke;
% names = originalDataset.Labels1StrokeChars;
% disp((originalDataset));
% % disp((names));
% dataset = Decimate(dataset,1,3);
% [x,y,z] = size(dataset);
% for i = 1:z
% %     disp(i);
%     dataset(:,:,i) = Centralize(dataset(:,:,i));
%     dataset(:,:,i) = NormalizeSize(dataset(:,:,i));
% end
% query = dataset(1,:,:);
% queryName = names(1,1);
% % disp(size(query));
% for i = 1 : x
%     fileName(i) = originalDataset.Labels1StrokeChars(i);
%     distance(i) = dtw(query,dataset(i,:,:),50);
% end
% % % disp(fileName);
% % disp(size(query));
% % disp(distance);
% 
% [sortedDist index] = sort(distance);
% disp(queryName);
% for i=1:x
%     disp(fileName(index(i)));
% end
end

