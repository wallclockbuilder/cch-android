
require 'sinatra'
require 'bundler/setup'
require 'json'


#Routes
get '/staying_well_info' do
	#content can be inspiration, news, religion, business, jokes, tip of the day,
	db = []
	content1 = '{"id":"123", "category":"joke", "content":"A bagpipe player was asked "How do you play that thing?" his answer was "Well.""}'
	content2 = '{"id":"124", "category":"tip", "content":"Did you know that you can check the time of the day by asking google.com?"}'
	content3 = '{"id":"125", "category":"business", "content":"Todays inflation rate is at 15.5%}'
	content4 = '{"id":"126", "category":"religion", "content":"John 3:16 God so loved the world, he gave..."}'
	content5 = '{"id":"127", "category":"news", "content":"14th February, valentines day is a holiday in Ghana."}'
	content6 = '{"id":"128", "category":"inspiration", "content":"I think one\'s feelings waste themselves in words; they ought all to be distilled into actions which bring results. -Florence Nightingale"}'
	db.push(content1)
	db.push(content2)
	db.push(content3)
	db.push(content4)
	db.push(content5)
	db.push(content6)
	serialized_info = db.to_json
	return serialized_info
end